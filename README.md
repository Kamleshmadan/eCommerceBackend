# 🛒 eCommerce Backend

A **Spring Boot**–based backend service for an **E-Commerce platform**, built with **Java 17** and **Maven**.  
It provides RESTful APIs for managing users, products, orders, and authentication — forming a solid foundation for a modern, scalable online store backend.

---

## 🚀 Features

✅ User registration and login  
✅ Product management (CRUD APIs)  
✅ Database integration via JPA/Hibernate  
✅ Layered architecture (Controller → Service → Repository)  
✅ Exception handling and input validation  
✅ JWT authentication support (extendable)  
✅ Easy configuration using `application.properties`

---

## 🧱 Tech Stack

| Layer | Technology |
|-------|-------------|
| Language | **Java 17+** |
| Framework | **Spring Boot 3** |
| Build Tool | **Maven** |
| ORM | **Hibernate / JPA** |
| Database | **MySQL / PostgreSQL** |
| API Format | **REST (JSON)** |
| Security | **Spring Security (JWT ready)** |

---

## 🧭 Future Enhancements

✅ Create Admin Role, Add admin dashboard and analytics  
✅ Create a new product (admin only)  
✅ Implement payment gateway integration

---

##  API Endpoints

**Base URL:** 'http://localhost:8085'

| Method   | Endpoint                                                 | Description                          | Auth Required? |
|----------|----------------------------------------------------------|--------------------------------------|----------------|
| `POST`   | `/auth/register`                                         | Register a new user                  | ❌             |
| `POST`   | `/auth/login`                                            | Authenticate the user & issue token  | ❌             |
| `GET`    | `/auth/me`                                               | Retrieve user profile by token       | ✅             |
| `POST`   | `/auth/verify?token={token}`                             | Verify email address                 | ❌             |
| `POST`   | `/auth/forgot?email={email}`                             | Send reset password link over mail   | ❌             |
| `POST`   | `/auth/reset`                                            | Reset password                       | ❌             |
| `GET`    | `/products`                                              | Get all products                     | ❌             |
| `GET`    | `/user/{userId}/address`                                 | Get address of a user                | ✅             |
| `POST`   | `/user/{userId}/address`                                 | Add a new address for user           | ✅             |
| `PATCH`  | `/user/{userId}/address/{addressId}`                     | Update existing address              | ✅             |
| `GET`    | `/cart/{userId}`                                         | Get cart products for user           | ✅             |
| `POST`   | `/cart/{userId}/add?productId={id}&quantity={qty}`       | Add product to user's cart           | ✅             |
| `PUT`    | `/cart/{userId}/update?productId={id}&quantity={qty}`    | Update quantity of product in cart   | ✅             |
| `DELETE` | `/cart/{userId}/remove?productId={id}`                   | Remove product from cart             | ✅             |

