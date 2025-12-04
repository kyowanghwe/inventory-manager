docker rm -f inventory-service
docker build -t inventory-manager .
docker run --name inventory-service -p 8080:8080 inventory-manager


Your application will be running and accessible at http://localhost:8080. You can test the CRUD endpoints using tools like cURL or Postman!

Test READ ALL: GET http://localhost:8080/api/v1/inventory

Access H2 Console (optional): http://localhost:8080/h2-console (use URL: jdbc:h2:mem:inventorydb, User: sa, Password: password)