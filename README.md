# User Management Demo

This repository contains a deliberately insecure Spring Boot user management application for demo and training purposes. It looks like a standard CRUD service, but it intentionally includes security flaws and technical debt to support demonstrations, secure code review exercises, and DevSecOps discussions.

## Stack

- Spring Boot 3.5.14
- Java 21
- Maven
- Spring Web, Spring Data JPA, H2, Spring Security, Actuator
- Swagger UI via springdoc-openapi

## Package Structure

- `controller`
- `service`
- `repository`
- `model`
- `config`

## Running Locally

1. Build the application:

   ```bash
   mvn clean package
   ```

2. Run the generated jar:

   ```bash
   java -jar target/user-management.jar
   ```

3. Override the port if required:

   ```bash
   set PORT=9090
   java -jar target/user-management.jar
   ```

## Useful URLs

- API base: `http://localhost:8080/users`
- Swagger UI: `http://localhost:8080/swagger-ui.html`
- OpenAPI docs: `http://localhost:8080/v3/api-docs`
- H2 console: `http://localhost:8080/h2-console`
- Actuator health: `http://localhost:8080/actuator/health`

## REST Endpoints

- `POST /users`
- `GET /users`
- `GET /users/{id}`
- `PUT /users/{id}`
- `DELETE /users/{id}`
- `GET /demo/slow`
- `GET /demo/sensitive-data`
- `GET /demo/search?email=...`
- `GET /demo/xss?input=...`

## Known Vulnerabilities

- Hardcoded Spring Security credentials in the application configuration.
- Passwords are stored and returned in plain text.
- CSRF is disabled.
- All endpoints are permitted without authorization.
- `/demo/search` builds a native SQL query by concatenating user input.
- `/demo/xss` reflects user input directly into an HTML response.
- `/demo/sensitive-data` exposes credentials and stored passwords without authentication.

## Intentional Technical Debt

- Field injection is used instead of constructor injection.
- Duplicate validation logic exists in the service layer.
- Long methods were left unrefactored intentionally.
- There is no proper global exception handling strategy.
- TODO comments mark places where production code should be cleaned up.

## Docker

1. Build the jar:

   ```bash
   mvn clean package
   ```

2. Build the container image:

   ```bash
   docker build -t user-management-demo .
   ```

3. Run the container:

   ```bash
   docker run -p 8080:8080 user-management-demo
   ```

## Notes

- This application is intentionally insecure and should never be used in production.
- The in-memory H2 database is recreated whenever the application restarts.