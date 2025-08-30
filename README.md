# Library app backend

## Technologies Used

### Core Framework
- **Spring Boot** - Main application framework
- **Spring Web MVC** - RESTful web services
- **Spring Data JPA** - Data persistence layer
- **Spring Security** - Authentication and authorization

### Database
- **PostgreSQL** - Production database
- **Hibernate** - ORM implementation

### Build & Dependency Management
- **Gradle** - Build tool and dependency management
- **Java 17+** - Programming language

### Additional Technologies
- **Validation API** - Input validation
- **Lombok** - Boilerplate code reduction
- **Swagger/OpenAPI 3** - API documentation

### Environment Variables

Set the following environment variables for production:
- **DB_USERNAME**
- **DB_PASSWORD**
- **JWT_SECRET_KEY**

### Swagger UI
Once the application is running, you can access the Swagger UI at:
```
http://localhost:8080/swagger-ui/index.html
```

## Security

- JWT-based authentication
- CORS configuration for cross-origin requests
- Input validation using Bean Validation


| Method | Endpoint                      | Description               |
|--------|-------------------------------|---------------------------|
| POST   | `/auth/login`                 | User authentication       |
| POST   | `/users/register`             | Register new user         |
| GET    | `/dictionaries`               | Get all dictionary data   |
| GET    | `/images`                     | Get image by filename     |
| GET    | `/books/search`               | Search books with filters |
| GET    | `/books/{id}`                 | Get book by ID            |
| POST   | `/books`                      | Create a new book         |
| POST   | `/books/{id}`                 | Update book               |
| DELETE | `/books/{id}`                 | Delete book               |
| GET    | `/books/autocomplete/titles`  | Get title suggestions     |
| GET    | `/books/autocomplete/authors` | Get author suggestions    |
| GET    | `/autocomplete/publishers`    | Get publisher suggestions |
