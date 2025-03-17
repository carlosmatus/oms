#  Order Management Microservice

A Spring Boot microservice that manages customer orders for an eCommerce platform. This service exposes RESTful APIs to place orders, fetch orders, and update order status, integrating with a mock Commerce Tools API and securing communication with JWT Authentication.

---
## ✨ Features

✅ Create orders after product validation  
✅ Retrieve orders by order ID  
✅ Update order status   ("Pending"+→+"Shipped"+→+"Delivered").  
✅ Mock Integration with CommerceTools API   
✅ JWT Authentication and Spring Security  
✅ Caching frequently accessed orders with Redis  
✅ Database migrations using Flyway

---

## Used technologies

| Technology        | Description               |
|-------------------|---------------------------|
| Java              | 17+                      |
| Spring Boot       | 3.x                      |
| Spring Data JPA   | ORM & Database Access    |
| PostgreSQL        | Relational Database      |
| Flyway            | Database Migrations      |
| Redis             | Caching Layer            |
| Spring Security   | JWT Authentication       |
| Log4j2            | Logging                  |
| Docker            | Containerization         |

---

## API Endpoints

| Method | Endpoint                                 | Description               |
|--------|------------------------------------------|---------------------------|
| POST   | `/orders`                                | Place a new order         |
| GET    | `/orders/{orderId}`                      | Retrieve an order by ID   |
| PUT  | `/orders/{orderId}/status`               | Update order status       |
| GET    | `/orders/customer/{customerId}`          | Fetch orders by customer ID |
| GET    | `/orders/status/{status}`          | Fetch orders by status |
| GET    | `/orders/customer/{customerId}/status/{status}`          | Fetch orders by specific customer ID and specific status|

---

### Secured Endpoints
All endpoints require JWT authentication via the `Authorization: Bearer <token>` header.

---

## Architecture Diagram

```
+------------------+        +--------------------+       +----------------+
|  Client / API    | <----> |  Order Microservice| <-->  | CommerceTools  |
| (Postman/UI/App) |        | (Spring Boot App)  |       | (Mock Service) |
+------------------+        +--------------------+       +----------------+
                                     |
                                     |
                                +-----------+
                                | PostgreSQL |
                                +-----------+
                                     |
                                +---------+
                                |   Redis  |
                                +---------+
```

---

##  Getting Started

### 1. Clone the Repository
```bash
git clone https://github.com/carlosmatus/oms/
cd oms
```

### 2. Build the Project
```bash
mvn clean install
```

### 3. Run Locally
```bash
mvn spring-boot:run
```

---

##  Docker Setup

### 1. Build Docker Image
```bash
docker build -t order-service:latest .
```

### 2. Run Docker Container
```bash
docker run -d -p 8080:8080 --name order-service order-service:latest
```

### 3. Docker postgress image
Create `Postgress image`:
```docker run --name oms-postgres \
-e POSTGRES_DB=orderdb \
-e POSTGRES_USER=admin \
-e POSTGRES_PASSWORD=admin123 \
-p 5432:5432 \
-d postgres:15
```
---

## Database Migrations with Flyway
Flyway automatically runs migrations on startup.

SQL files are located in:
```
src/main/resources/db/migration
```

---

## Caching
- **Redis** is used for caching order details.
- Cached for faster reads on frequently accessed endpoints like `GET /orders/{orderId}`.

---

## Security
- Endpoints are secured with **Spring Security**.
- **JWT Authentication** required.
---

## Example cURL Commands

### Authenticate
```bash
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username": "user", "password": "password"}'
```

### Place Order
```bash
curl -X POST http://localhost:8080/orders \
  -H "Authorization: Bearer <token>" \
  -H "Content-Type: application/json" \
  -d '{
        "customerId": 1,
        "productId": 10,
        "quantity": 2
      }'
```

### Get Order
```bash
curl -X GET http://localhost:8080/orders/1 \
  -H "Authorization: Bearer <token>"
```
### Get Orders by Customer ID and Status
```bash
curl -X GET http://localhost:8080/orders/customer/1/status/SHIPPED \
  -H "Authorization: Bearer <token>"
```
```
### Get Orders by Status
```bash
curl -X GET http://localhost:8080/orders/status/PENDING \
  -H "Authorization: Bearer <token>"
```
---

##  Author
**Carlos Matus**

[GitHub](https://github.com/carlosmatus/oms/)

---