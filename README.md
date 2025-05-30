# 📝 Blog Web Application

A full-featured blog platform built with **Spring Boot 3**, **PostgreSQL**, and **Spring Security with JWT**. The application supports user registration, post and comment creation, likes, and role-based access control.

---

## ⚙️ Tech Stack

- **Java 21**
- **Spring Boot 3.2.5**
- **Spring Web**
- **Spring Data JPA**
- **Spring Security (JWT)**
- **PostgreSQL**
- **Lombok**
- **Spring Validation**
- **OpenAPI/Swagger 3**
- **Spring AOP**
- **Spring Boot Actuator**

---

## 🏗️ Architecture Overview

- **User**: stores user profile information.
- **Security**: separate entity for authentication (login, password, role).
- **Post**: user-generated blog posts.
- **Comment**: comments to posts.
- **Like**: likes to both posts and comments.

> DTOs are clearly separated into request and response types.

> Business logic is split into services (`Service` + `ServiceImpl`), with validation, exception handling, and AOP logging.

---

## ▶️ Getting Started

### 🔧 Prerequisites

- Java 21
- Maven
- PostgreSQL database

### 🛠️ Configuration

Update your `application.yml` or `application.properties`:

```properties
## Database config:
spring.datasource.url=jdbc:postgresql://localhost:5432/blog_db
spring.datasource.username=user32
spring.datasource.password=qwerty

server.port=8080

## Spring Data JPA
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect

## Logs
logging.level.root=INFO
logging.level.org.hibernate.SQL=DEBUG
logging.level.org.hibernate.orm.jdbc.bind=TRACE
logging.file.name=logs/logs.log

## Security JWT
jwt.secret=your_secret
jwt.expiration=your_expiration_time

## Actuator
management.endpoints.web.exposure.include=info,env,health,metrics
management.endpoint.shutdown.enabled=false
```
## Only for admins!
### 📑 Swagger API Docs
- Swagger UI is available at:
```
http://localhost:8080/swagger-ui/index.html
```

### 📊 Monitoring
- Spring Boot Actuator endpoints are available at:
```
http://localhost:8080/actuator
```

## 📘 API Overview
- This RESTful API supports CRUD operations for users, posts, comments, and likes, as well as secure login and registration using JWT.

## 🛡️ Security
- JWT-based authentication with token expiration
- Role-based access control (**USER**, **ADMIN**)
- Endpoints protected via filter
- AOP used for logging
  
## Endpoints:
- ### ®️ /registration
- ### 🔑 /login
- ### 🔐 /security
| Method | Endpoint         | Description              | Role       |
| ------ | ---------------- | ------------------------ | ---------- |
| GET    | `/security/{id}` | Get security info by ID  | ADMIN      |
| PUT    | `/security`      | Update login or password | USER/ADMIN |
- ### 📝 /posts
| Method | Endpoint                 | Description               | Role       |
| ------ | ------------------------ | ------------------------- | -----------|
| POST   | `/posts`                 | Create a new post         | USER/ADMIN |
| GET    | `/posts/{id}`            | Get post by ID            | USER/ADMIN |
| GET    | `/posts/find/{username}` | Get all posts by username | USER/ADMIN |
| GET    | `/posts/find/all`        | Get all posts             | USER/ADMIN |
| PUT    | `/posts`                 | Update post               | USER/ADMIN |
| DELETE | `/posts/{id}`            | Delete post by ID         | USER/ADMIN |
| GET    | `/posts/countLikes/{id}` | Count likes for a post    | USER/ADMIN |
- ### 💬 /comments
| Method | Endpoint                    | Description                   | Role       |
| ------ | --------------------------- | ----------------------------- | ---------- |
| POST   | `/comments`                 | Create a new comment          | USER/ADMIN |
| GET    | `/comments/{id}`            | Get comment by ID             | USER/ADMIN |
| GET    | `/comments/inPost/{id}`     | Get all comments under a post | USER/ADMIN |
| PUT    | `/comments`                 | Update comment                | USER/ADMIN |
| DELETE | `/comments/{id}`            | Delete comment by ID          | USER/ADMIN |
| GET    | `/comments/countLikes/{id}` | Count likes for a comment     | USER/ADMIN |
- ### ❤️ /likes
| Method | Endpoint                     | Description           | Role       |
| ------ | ---------------------------- | --------------------- | ---------- |
| POST   | `/likes/post/{postId}`       | Like/unlike a post    | USER/ADMIN |
| POST   | `/likes/comment/{commentId}` | Like/unlike a comment | USER/ADMIN |

## Quick start
### 1. Clone repository
```console
git clone https://github.com/sosopyl61/blog-web-application.git
```
### 2. Execute blog_db.sql to create the database.

### Enjoy!
