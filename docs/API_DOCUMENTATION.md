# E-Commerce Demo API Documentation

A comprehensive Spring Boot REST API for an e-commerce platform with authentication, product management, order processing, and admin functionalities.

---

## Table of Contents

1. [Overview](#overview)
2. [Technology Stack](#technology-stack)
3. [Getting Started](#getting-started)
4. [Authentication](#authentication)
5. [API Endpoints](#api-endpoints)
   - [Auth Endpoints](#auth-endpoints)
   - [Product Endpoints](#product-endpoints)
   - [Order Endpoints](#order-endpoints)
   - [Product Review Endpoints](#product-review-endpoints)
6. [Data Models](#data-models)
7. [Error Handling](#error-handling)
8. [Postman Collection Setup](#postman-collection-setup)

---

## Overview

This is a RESTful e-commerce API that provides:

- **User Authentication**: JWT-based registration and login
- **Product Management**: Browse, search, and filter products
- **Order Management**: Create and track orders
- **Product Reviews**: Add reviews to products

## Technology Stack

| Component        | Technology              |
|------------------|-------------------------|
| Framework        | Spring Boot             |
| Database         | PostgreSQL              |
| Authentication   | JWT (JSON Web Tokens)   |
| Security         | Spring Security         |
| Build Tool       | Maven                   |

---

## Getting Started

### Prerequisites

- Java 17+
- PostgreSQL Database
- Maven

### Environment Variables

Set the following environment variables before running:

```properties
DB_URL=jdbc:postgresql://localhost:5432/ecomdemo
DB_USERNAME=your_db_username
DB_PASSWORD=your_db_password
JWT_SECRET=YourBase64EncodedSecretKeyHere256BitsMinimumRequiredForHS256Algorithm
```

### Running the Application

```bash
./mvnw spring-boot:run
```

The server runs on `http://localhost:8080` by default.

---

## Authentication

This API uses **JWT Bearer Token** authentication.

### How Authentication Works

1. Register a new user or login with existing credentials
2. Receive a JWT token in the response
3. Include the token in the `Authorization` header for protected endpoints

### Authorization Header Format

```
Authorization: Bearer <your_jwt_token>
```

### User Roles

| Role    | Description                                    |
|---------|------------------------------------------------|
| `USER`  | Regular user - can browse, order, review       |

### Access Control Summary

| Endpoint Pattern            | Access Level        |
|-----------------------------|---------------------|
| `POST /api/auth/**`         | Public              |
| `GET /api/products/**`      | Public              |
| `POST /api/products/reviews`| Authenticated Users |
| `/api/orders/**`            | Authenticated Users |

---

## API Endpoints

### Base URL

```
http://localhost:8080
```

---

## Auth Endpoints

### 1. Register User

Create a new user account.

**Endpoint:** `POST /api/auth/register`

**Access:** Public

**Request Body:**

```json
{
  "name": "John Doe",
  "email": "john@example.com",
  "password": "Password123"
}
```

**Validation Rules:**

| Field    | Rules                                                                                      |
|----------|--------------------------------------------------------------------------------------------|
| name     | Required, 2-100 characters, letters and spaces only                                        |
| email    | Required, valid email format, max 255 characters                                           |
| password | Required, 8-128 characters, must contain uppercase, lowercase, and number                  |

**Success Response (201 Created):**

```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "type": "Bearer",
  "userId": 1,
  "name": "John Doe",
  "email": "john@example.com",
  "role": "USER"
}
```

**Error Response (409 Conflict):**

```json
{
  "status": 409,
  "message": "User with email john@example.com already exists",
  "timestamp": "2026-02-24T10:30:00"
}
```

---

### 2. Login

Authenticate and receive a JWT token.

**Endpoint:** `POST /api/auth/login`

**Access:** Public

**Request Body:**

```json
{
  "email": "john@example.com",
  "password": "Password123"
}
```

**Validation Rules:**

| Field    | Rules                              |
|----------|------------------------------------|
| email    | Required, valid email format       |
| password | Required, max 128 characters       |

**Success Response (200 OK):**

```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "type": "Bearer",
  "userId": 1,
  "name": "John Doe",
  "email": "john@example.com",
  "role": "USER"
}
```

**Error Response (401 Unauthorized):**

```json
{
  "status": 401,
  "message": "Invalid email or password",
  "timestamp": "2026-02-24T10:30:00"
}
```

---

### 3. Get Current User

Retrieve the currently authenticated user's information.

**Endpoint:** `GET /api/auth/me`

**Access:** Authenticated Users

**Headers:**

```
Authorization: Bearer <token>
```

**Success Response (200 OK):**

```json
{
  "id": 1,
  "name": "John Doe",
  "email": "john@example.com",
  "role": "USER"
}
```

---

## Product Endpoints

### 1. Get All Products (Paginated)

Retrieve products with pagination.

**Endpoint:** `GET /api/products`

**Access:** Public

**Query Parameters:**

| Parameter | Type    | Default | Description              |
|-----------|---------|---------|--------------------------|
| page      | integer | 0       | Page number (0-indexed)  |
| size      | integer | 5       | Number of items per page |

**Example Request:**

```
GET /api/products?page=0&size=10
```

**Success Response (200 OK):**

```json
{
  "products": [
    {
      "id": 1,
      "name": "Wireless Headphones",
      "price": 99.99,
      "description": "High-quality wireless headphones",
      "category": "Electronics",
      "ratings": 4.5,
      "seller": "TechStore",
      "stock": 50,
      "numOfReviews": 10,
      "reviews": [
        {
          "productId": 1,
          "comment": "Great sound quality!",
          "rating": 5.0
        }
      ],
      "images": [
        {
          "publicId": "products/headphones_001"
        }
      ]
    }
  ],
  "totalProducts": 100
}
```

---

### 2. Get Product by ID

Retrieve a single product by its ID.

**Endpoint:** `GET /api/products/{id}`

**Access:** Public

**Path Parameters:**

| Parameter | Type | Description      |
|-----------|------|------------------|
| id        | Long | Product ID       |

**Example Request:**

```
GET /api/products/1
```

**Success Response (200 OK):**

```json
{
  "id": 1,
  "name": "Wireless Headphones",
  "price": 99.99,
  "description": "High-quality wireless headphones",
  "category": "Electronics",
  "ratings": 4.5,
  "seller": "TechStore",
  "stock": 50,
  "numOfReviews": 10,
  "reviews": [],
  "images": []
}
```

---

### 3. Search Products

Search and filter products by various criteria.

**Endpoint:** `GET /api/products/search`

**Access:** Public

**Query Parameters:**

| Parameter | Type   | Required | Description                     |
|-----------|--------|----------|---------------------------------|
| category  | String | No       | Filter by category              |
| minPrice  | Double | No       | Minimum price                   |
| maxPrice  | Double | No       | Maximum price                   |
| keyword   | String | No       | Search keyword in product name  |
| ratings   | Double | No       | Minimum rating                  |

**Example Requests:**

```
GET /api/products/search?category=Electronics
GET /api/products/search?minPrice=50&maxPrice=200
GET /api/products/search?keyword=headphones&ratings=4.0
GET /api/products/search?category=Electronics&minPrice=100&keyword=wireless
```

**Success Response (200 OK):**

```json
[
  {
    "id": 1,
    "name": "Wireless Headphones",
    "price": 99.99,
    "description": "High-quality wireless headphones",
    "category": "Electronics",
    "ratings": 4.5,
    "seller": "TechStore",
    "stock": 50,
    "numOfReviews": 10,
    "reviews": [],
    "images": []
  }
]
```

---

## Order Endpoints

### 1. Create Order

Place a new order (requires authentication).

**Endpoint:** `POST /api/orders`

**Access:** Authenticated Users

**Headers:**

```
Authorization: Bearer <token>
Content-Type: application/json
```

**Request Body:**

```json
{
  "orderItems": [
    {
      "name": "Wireless Headphones",
      "quantity": 2,
      "image": "products/headphones_001",
      "price": 99.99,
      "productId": 1
    },
    {
      "name": "Phone Case",
      "quantity": 1,
      "image": "products/case_002",
      "price": 19.99,
      "productId": 2
    }
  ]
}
```

**Order Item Fields:**

| Field     | Type    | Description                   |
|-----------|---------|-------------------------------|
| name      | String  | Product name                  |
| quantity  | Integer | Quantity to order             |
| image     | String  | Product image ID              |
| price     | Double  | Price per unit                |
| productId | Long    | ID of the product             |

**Success Response (200 OK):**

```json
{
  "referenceId": "550e8400-e29b-41d4-a716-446655440000"
}
```

**Business Logic:**

- Stock is automatically reduced for each ordered item
- Order calculation:
  - `totalItemsAmount` = sum of (price × quantity) for all items
  - `taxAmount` = totalItemsAmount × 10%
  - `totalAmount` = totalItemsAmount + taxAmount
- Initial order status is set to "pending"

**Error Response (400 Bad Request):**

```json
{
  "status": 400,
  "message": "Insufficient stock for product: Wireless Headphones. Available: 5",
  "timestamp": "2026-02-24T10:30:00"
}
```

---

### 2. Get Order by Order Number

Retrieve a specific order by its reference ID.

**Endpoint:** `GET /api/orders/{orderNo}`

**Access:** Authenticated Users (own orders only)

**Headers:**

```
Authorization: Bearer <token>
```

**Path Parameters:**

| Parameter | Type   | Description              |
|-----------|--------|--------------------------|
| orderNo   | String | Order reference number   |

**Example Request:**

```
GET /api/orders/550e8400-e29b-41d4-a716-446655440000
```

**Success Response (200 OK):**

```json
{
  "id": 1,
  "orderItems": [
    {
      "id": 1,
      "name": "Wireless Headphones",
      "quantity": 2,
      "image": "products/headphones_001",
      "price": 99.99
    }
  ],
  "totalItemsAmount": 199.98,
  "taxAmount": 19.998,
  "totalAmount": 219.978,
  "status": "pending",
  "orderNo": "550e8400-e29b-41d4-a716-446655440000",
  "createdAt": "2026-02-24T10:30:00",
  "updatedAt": "2026-02-24T10:30:00"
}
```

---

### 3. Get User Orders (Paginated)

Retrieve all orders for the authenticated user.

**Endpoint:** `GET /api/orders`

**Access:** Authenticated Users

**Headers:**

```
Authorization: Bearer <token>
```

**Query Parameters:**

| Parameter | Type    | Default | Description              |
|-----------|---------|---------|--------------------------|
| page      | integer | 0       | Page number (0-indexed)  |
| size      | integer | 10      | Number of items per page |

**Example Request:**

```
GET /api/orders?page=0&size=10
```

**Success Response (200 OK):**

```json
{
  "orders": [
    {
      "id": 1,
      "orderItems": [...],
      "totalItemsAmount": 199.98,
      "taxAmount": 19.998,
      "totalAmount": 219.978,
      "status": "pending",
      "orderNo": "550e8400-e29b-41d4-a716-446655440000",
      "createdAt": "2026-02-24T10:30:00",
      "updatedAt": "2026-02-24T10:30:00"
    }
  ],
  "totalOrders": 15,
  "totalPages": 2,
  "currentPage": 0
}
```

---

## Product Review Endpoints

### 1. Add Product Review

Submit a review for a product.

**Endpoint:** `POST /api/products/reviews`

**Access:** Authenticated Users

**Headers:**

```
Authorization: Bearer <token>
Content-Type: application/json
```

**Request Body:**

```json
{
  "productId": 1,
  "comment": "Excellent quality! Highly recommended.",
  "rating": 4.5
}
```

**Validation Rules:**

| Field     | Type   | Rules                        |
|-----------|--------|------------------------------|
| productId | Long   | Required                     |
| comment   | String | Required, cannot be blank    |
| rating    | Double | Required                     |

**Success Response (201 Created):**

```
"Review added"
```

---

## Data Models

### User Entity

| Field     | Type          | Description                      |
|-----------|---------------|----------------------------------|
| id        | Long          | Unique identifier                |
| name      | String        | User's full name                 |
| email     | String        | User's email (unique)            |
| password  | String        | Encrypted password               |
| role      | Role (enum)   | USER or ADMIN                    |
| createdAt | LocalDateTime | Account creation timestamp       |
| updatedAt | LocalDateTime | Last update timestamp            |

### Product Entity

| Field        | Type                | Description                 |
|--------------|---------------------|-----------------------------|
| id           | Long                | Unique identifier           |
| name         | String              | Product name                |
| price        | Double              | Product price               |
| description  | String              | Product description         |
| category     | String              | Product category            |
| ratings      | Double              | Average rating (0-5)        |
| seller       | String              | Seller name                 |
| stock        | Integer             | Available stock quantity    |
| numOfReviews | Integer             | Total number of reviews     |
| images       | List<ProductImage>  | Product images              |
| reviews      | List<ProductReview> | Product reviews             |

### Order Entity

| Field            | Type              | Description                 |
|------------------|-------------------|-----------------------------|
| id               | Long              | Unique identifier           |
| user             | User              | Order owner                 |
| orderItems       | List<OrderItem>   | Items in the order          |
| totalItemsAmount | Double            | Subtotal before tax         |
| taxAmount        | Double            | Tax amount (10%)            |
| totalAmount      | Double            | Total including tax         |
| status           | String            | Order status                |
| orderNo          | String            | Unique order reference      |
| createdAt        | LocalDateTime     | Order creation timestamp    |
| updatedAt        | LocalDateTime     | Last update timestamp       |

### OrderItem Entity

| Field    | Type    | Description           |
|----------|---------|-----------------------|
| id       | Long    | Unique identifier     |
| name     | String  | Product name          |
| quantity | Integer | Quantity ordered      |
| image    | String  | Product image ID      |
| price    | Double  | Price per unit        |
| product  | Product | Associated product    |

---

## Error Handling

The API uses standardized error responses:

### Error Response Format

```json
{
  "status": 400,
  "message": "Error description",
  "timestamp": "2026-02-24T10:30:00",
  "errors": {
    "fieldName": "Field-specific error message"
  }
}
```

### Common HTTP Status Codes

| Status Code | Description                                         |
|-------------|-----------------------------------------------------|
| 200         | Success                                             |
| 201         | Created successfully                                |
| 400         | Bad Request - validation errors                     |
| 401         | Unauthorized - invalid/missing token                |
| 403         | Forbidden - insufficient permissions                |
| 404         | Not Found - resource doesn't exist                  |
| 409         | Conflict - duplicate resource (e.g., email exists)  |
| 500         | Internal Server Error                               |

### Validation Error Example

```json
{
  "status": 400,
  "message": "Validation failed",
  "timestamp": "2026-02-24T10:30:00",
  "errors": {
    "email": "Please provide a valid email address",
    "password": "Password must be between 8 and 128 characters",
    "name": "Name can only contain letters and spaces"
  }
}
```

---

## Postman Collection Setup

### Setting Up Environment Variables

1. Create a new Postman Environment
2. Add the following variables:

| Variable   | Initial Value              | Description             |
|------------|----------------------------|-------------------------|
| base_url   | http://localhost:8080      | API base URL            |
| token      |                            | JWT token (auto-filled) |

### Auto-Save Token Script

Add this script to the **Tests** tab of Login/Register requests:

```javascript
if (pm.response.code === 200 || pm.response.code === 201) {
    var jsonData = pm.response.json();
    pm.environment.set("token", jsonData.token);
}
```

### Sample Request Collection Structure

```
📁 E-Commerce API
├── 📁 Auth
│   ├── Register User
│   ├── Login
│   └── Get Current User
├── 📁 Products (Public)
│   ├── Get All Products
│   ├── Get Product by ID
│   └── Search Products
├── 📁 Orders (User)
│   ├── Create Order
│   ├── Get Order by Number
│   └── Get My Orders
└── 📁 Reviews (User)
    └── Add Product Review
```

### Testing Workflow

1. **Start the server** - Run the Spring Boot application
2. **Register a user** - `POST /api/auth/register`
3. **Login** - `POST /api/auth/login` (token auto-saved)
4. **Browse products** - `GET /api/products` (no auth needed)
5. **Create an order** - `POST /api/orders` (with token)

---

## Quick Reference - All Endpoints

| Method | Endpoint                              | Access          | Description              |
|--------|---------------------------------------|-----------------|--------------------------|
| POST   | /api/auth/register                    | Public          | Register new user        |
| POST   | /api/auth/login                       | Public          | User login               |
| GET    | /api/auth/me                          | Authenticated   | Get current user         |
| GET    | /api/products                         | Public          | Get all products         |
| GET    | /api/products/{id}                    | Public          | Get product by ID        |
| GET    | /api/products/search                  | Public          | Search products          |
| POST   | /api/products/reviews                 | Authenticated   | Add product review       |
| POST   | /api/orders                           | Authenticated   | Create order             |
| GET    | /api/orders/{orderNo}                 | Authenticated   | Get order by number      |
| GET    | /api/orders                           | Authenticated   | Get user's orders        |

---

**Happy Testing!** 🚀
````
