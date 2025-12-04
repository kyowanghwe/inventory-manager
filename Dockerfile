# ----------------------------------------
# STAGE 1: Build the Application
# ----------------------------------------
# Use a Java JDK base image for compiling and building the JAR
FROM eclipse-temurin:17-jdk AS build

# Set the working directory inside the container
WORKDIR /app

# Copy the Maven wrapper script (mvnw) and its configuration (.mvn)
COPY mvnw .
COPY .mvn .mvn
RUN chmod +x mvnw

# Copy the Maven project file to download dependencies
COPY pom.xml .

# Download dependencies (this step is cached if pom.xml doesn't change)
RUN --mount=type=cache,target=/root/.m2 ./mvnw dependency:go-offline

# Copy the rest of the source code
COPY src ./src

# Build the Spring Boot application, creating the executable JAR
RUN ./mvnw package -DskipTests

# ----------------------------------------
# STAGE 2: Create the Final Production Image
# ----------------------------------------
# Use a smaller, secure JRE base image for the final runtime image
FROM eclipse-temurin:17-jre-jammy AS runtime

# Expose the default Spring Boot port
EXPOSE 8080

# Set a volume mount for potential external logs/data
VOLUME /tmp

# Copy the executable JAR from the build stage
ARG JAR_FILE=/app/target/*.jar
COPY --from=build ${JAR_FILE} app.jar

# Define the entrypoint to run the application
# Use 'exec' form for proper signal handling in the container
ENTRYPOINT ["java", "-jar", "/app.jar"]