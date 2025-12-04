# 📦 Inventory Service

A simple Inventory Management Service running with Spring Boot, packaged using Docker, and equipped with Swagger UI and an H2 in-memory database.

---

## 🚀 Run with Docker

Use the following commands to remove any existing container, build the image, and run the application:

docker rm -f inventory-service
docker build -t inventory-manager .
docker run --name inventory-service -p 8080:8080 inventory-manager

---

## 🌐 Application URLs

Base URL:
http://localhost:8080

Swagger UI:
http://localhost:8080/swagger-ui/index.html

H2 Console (optional):
http://localhost:8080/h2-console

H2 Credentials:
- JDBC URL: jdbc:h2:mem:inventorydb
- User: sa
- Password: password

---

## 🔍 API Testing

READ ALL inventory items (GET):
http://localhost:8080/api/v1/inventory

Use tools like Postman, cURL, or Insomnia to test the endpoints.

---

## 🛠️ Tech Stack

- Spring Boot
- Spring Web
- Spring Data JPA
- H2 Database
- Swagger OpenAPI
- Docker

---

## 📄 License

This project is provided for learning and demo purposes.
