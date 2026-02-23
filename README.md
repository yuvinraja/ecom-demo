# E-Commerce Backend API

A fully-featured RESTful e-commerce backend built with **Spring Boot 4.0.3** and **Java 21**, demonstrating enterprise-level patterns and best practices.

## 🚀 Features

### Authentication & Authorization
- **JWT-based authentication** with secure token generation
- Role-based access control (USER, ADMIN)
- Password encryption using BCrypt
- Protected endpoints with Spring Security

### Product Management
- Full CRUD operations for products (Admin only)
- Pagination and sorting support
- Advanced search with JPA Specifications:
  - Filter by category
  - Filter by price range
  - Search by keyword (name/description)
  - Filter by minimum rating
- Product reviews with ratings

### Order Management
- Create orders with automatic stock validation
- Order history per user
- Order status management (Admin)
- Automatic tax calculation (10%)
- Stock reduction on order placement

### Admin Dashboard
- User management
- Order status updates
- Dashboard statistics (total revenue, order counts, etc.)
- Product inventory management

## 🛠️ Tech Stack

- **Framework:** Spring Boot 4.0.3
- **Language:** Java 21
- **Database:** PostgreSQL
- **Security:** Spring Security + JWT (jjwt 0.12.6)
- **Build:** Maven
- **Validation:** Jakarta Bean Validation

## 📁 Project Structure

```
src/main/java/com/yuvin/ecomdemo/
├── config/          # Security configuration
├── controller/      # REST controllers
├── dto/             # Data Transfer Objects
├── entity/          # JPA entities
├── repository/      # JPA repositories
├── security/        # JWT filter & services
├── service/         # Business logic
└── spec/            # JPA Specifications for queries
```

## 🔐 API Endpoints

### Authentication (`/api/auth`)
| Method | Endpoint | Description | Access |
|--------|----------|-------------|--------|
| POST | `/register` | Register new user | Public |
| POST | `/login` | User login | Public |
| GET | `/me` | Get current user info | Authenticated |

### Products (`/api/products`)
| Method | Endpoint | Description | Access |
|--------|----------|-------------|--------|
| GET | `/` | Get all products (paginated) | Public |
| GET | `/{id}` | Get product by ID | Public |
| GET | `/search` | Search products with filters | Public |
| POST | `/reviews` | Add a product review | Authenticated |

### Orders (`/api/orders`)
| Method | Endpoint | Description | Access |
|--------|----------|-------------|--------|
| POST | `/` | Create new order | Authenticated |
| GET | `/` | Get user's orders | Authenticated |
| GET | `/{orderNo}` | Get order by number | Authenticated |

### Admin (`/api/admin`)
| Method | Endpoint | Description | Access |
|--------|----------|-------------|--------|
| POST | `/products` | Create product | Admin |
| PUT | `/products/{id}` | Update product | Admin |
| DELETE | `/products/{id}` | Delete product | Admin |
| GET | `/orders` | Get all orders | Admin |
| PUT | `/orders/{orderNo}/status` | Update order status | Admin |
| GET | `/users` | Get all users | Admin |
| DELETE | `/users/{id}` | Delete user | Admin |
| GET | `/dashboard/stats` | Get dashboard statistics | Admin |

## 🚦 Getting Started

### Prerequisites
- Java 21+
- Maven 3.8+
- PostgreSQL 14+

### Environment Variables
Create a `.env` file or set environment variables:
```env
DB_URL=jdbc:postgresql://localhost:5432/ecomdemo
DB_USERNAME=your_username
DB_PASSWORD=your_password
JWT_SECRET=your_base64_encoded_secret_key
```

### Run the Application
```bash
# Clone the repository
git clone <repository-url>

# Navigate to project directory
cd ecomdemo

# Run with Maven
./mvnw spring-boot:run
```

## 📝 API Examples

### Register User
```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "name": "John Doe",
    "email": "john@example.com",
    "password": "password123"
  }'
```

### Login
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "john@example.com",
    "password": "password123"
  }'
```

### Search Products
```bash
curl "http://localhost:8080/api/products/search?category=Electronics&minPrice=50&maxPrice=500"
```

### Create Order
```bash
curl -X POST http://localhost:8080/api/orders \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <your-jwt-token>" \
  -d '{
    "orderItems": [
      {
        "productId": 1,
        "name": "iPhone 15",
        "price": 999.99,
        "quantity": 1,
        "image": "iphone.jpg"
      }
    ]
  }'
```

## 🏗️ Architecture Highlights

### Security
- Stateless JWT authentication
- BCrypt password hashing
- Role-based endpoint protection
- Custom JWT filter with token validation

### Database
- JPA/Hibernate with PostgreSQL
- Proper entity relationships (OneToMany, ManyToOne)
- Audit fields (createdAt, updatedAt)
- JPA Specifications for dynamic queries

## 📊 Database Schema

```
users
├── id (PK)
├── name
├── email (unique)
├── password (encrypted)
├── role (USER/ADMIN)
├── created_at
└── updated_at

products
├── id (PK)
├── name
├── price
├── description
├── category
├── ratings
├── seller
├── stock
└── num_of_reviews

orders
├── id (PK)
├── user_id (FK)
├── order_no (unique)
├── status
├── total_items_amount
├── tax_amount
├── total_amount
├── created_at
└── updated_at

order_items
├── id (PK)
├── order_id (FK)
├── product_id (FK)
├── name
├── price
├── quantity
└── image

product_reviews
├── id (PK)
├── product_id (FK)
├── rating
└── comment

product_images
├── id (PK)
├── product_id (FK)
├── public_id
└── url
```

## 🔮 Future Enhancements

- [ ] Payment gateway integration (Stripe/PayPal)
- [ ] Email notifications
- [ ] Redis caching
- [ ] Docker containerization
- [ ] Swagger/OpenAPI documentation
- [ ] Rate limiting
- [ ] Wishlist functionality
- [ ] Product categories CRUD
- [ ] Discount/coupon system

## 👤 Author

**Yuvin** - Full Stack Developer

## 📄 License

This project is licensed under the MIT License.
