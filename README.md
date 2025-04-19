# Auth Microservice

A flexible authentication microservice built with Spring Boot and MongoDB that supports customizable registration fields and authentication flows.

## Features

- Email and password-based authentication
- Customizable registration fields
- Domain and role-based configuration
- JWT-based authentication
- Refresh token support
- Password reset functionality
- MongoDB integration
- Spring Security integration

## Prerequisites

- Java 17 or higher
- Maven
- MongoDB
- Spring Boot 3.2.3

## Configuration

The application can be configured using the `application.yml` file. Key configurations include:

- MongoDB connection settings
- JWT secret and expiration
- Allowed domains and roles
- Mandatory and optional registration fields

## API Endpoints

### Authentication

- `POST /api/auth/register` - Register a new user
- `POST /api/auth/login` - Login with email and password
- `POST /api/auth/refresh` - Refresh access token
- `POST /api/auth/logout` - Logout user
- `POST /api/auth/forgot-password` - Request password reset
- `POST /api/auth/reset-password` - Reset password with token

## Security

- JWT-based authentication
- Password encryption using BCrypt
- Role-based access control
- Domain-based access control
- CSRF protection
- Stateless session management

## Project Structure

```
auth-service/
├── controller/         # REST controllers
├── dto/               # Data Transfer Objects
├── entity/            # MongoDB entities
├── repository/        # MongoDB repositories
├── security/          # Security configuration
│   ├── JwtFilter.java
│   ├── JwtUtil.java
│   ├── SecurityConfig.java
├── service/           # Business logic
└── AuthServiceApplication.java
```

## Getting Started

1. Clone the repository
2. Configure MongoDB connection in `application.yml`
3. Build the project: `mvn clean install`
4. Run the application: `mvn spring-boot:run`

## License

This project is licensed under the MIT License. 