# Do-It: Notes API

A RESTfull API built with Spring Boot for managing personal notes with JWT authentication.

## 📋 Overview

Do-It is a secure and scalable backend service that allows users to create, read, update, and delete personal notes. The application includes user authentication with JWT tokens and provides a complete API for note management.

## ✨ Features

* **User Management**
   * User registration and authentication
   * JWT-based security
* **Notes Management**
   * Create, read, update, and delete notes
   * Mark notes as completed/uncompleted
   * Filter notes by status
* **API Documentation**
   * Swagger UI for easy API exploration
   * Comprehensive endpoint documentation

## 🛠️ Technologies

* **Java 17**
* **Spring Boot 3**
* **Spring Security** with JWT
* **PostgreSQL** database
* **Docker** & Docker Compose
* **Swagger/OpenAPI** for documentation
* **Lombok** for reducing boilerplate code

## 🚀 Getting Started

### Prerequisites

* JDK 17 or higher
* Maven
* Docker and Docker Compose (for containerized deployment)

### Environment Variables

The application uses the following environment variables:

```
SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/notesdb
SPRING_DATASOURCE_USERNAME=postgres
SPRING_DATASOURCE_PASSWORD=postgres
JWT_SECRET=your_jwt_secret_key
JWT_EXPIRATION=86400000
SERVER_PORT=8080
```

### Running Locally

1. Clone the repository:

```bash
git clone https://github.com/schillreff/do-it-api.git
cd do-it
```

2. Build the application:

```bash
mvn clean package
```

3. Run the application:

```bash
java -jar target/do-it-1.0.0.jar
```

### Running with Docker

1. Build and start the containers:

```bash
docker-compose up -d
```

2. The application will be available at: http://localhost:8080

## 📚 API Documentation

Once the application is running, you can access the Swagger UI documentation at:

```
http://localhost:8080/swagger-ui.html
```

## 🔒 Authentication

The API uses JWT tokens for authentication. To access protected endpoints:
1. Register a new user at `/api/auth/register`
2. Login at `/api/auth/login` to get a token
3. Include the token in the Authorization header for subsequent requests:

```
Authorization: Bearer your_jwt_token
```

## 🌐 API Endpoints

### Authentication
* `POST /api/auth/register` - Register a new user
* `POST /api/auth/login` - Authenticate and get JWT token

### Notes
* `GET /api/notes` - Get all notes for the authenticated user
* `GET /api/notes/{noteId}` - Get a specific note
* `POST /api/notes` - Create a new note
* `PUT /api/notes/{noteId}` - Update a note
* `DELETE /api/notes/{noteId}` - Delete a note
* `PATCH /api/notes/{noteId}/complete` - Mark a note as completed
* `PATCH /api/notes/{noteId}/uncomplete` - Mark a note as not completed

### Users
* `GET /api/users/{userId}` - Get user details
* `PUT /api/users/{userId}` - Update user details
* `DELETE /api/users/{userId}` - Delete user account

## 🧪 Testing

Run the tests with:

```bash
mvn test
```

## 📦 Project Structure

```
src
├── main
│   ├── java
│   │   └── dev
│   │       └── leandroschillreff
│   │           └── do_it
│   │               ├── config          # Configuration classes
│   │               ├── controller      # REST controllers
│   │               ├── dto             # Data transfer objects
│   │               ├── entity          # JPA entities
│   │               ├── exception       # Custom exceptions and handlers
│   │               ├── repository      # JPA repositories
│   │               ├── security        # JWT and security configuration
│   │               ├── service         # Business logic
│   │               └── util            # Utility classes
│   └── resources
│       └── application.properties      # Application configuration
└── test                                # Test classes
```

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 👨‍💻 Author

* **Leandro Schillreff** - Initial work and maintenance