# Eldorg Spring Boot MVP

## Overview

Eldorg is a modular Spring Boot MVP project following Clean Architecture principles. It provides a robust foundation for
scalable, maintainable, and testable Java backend services, featuring JWT authentication, PostgreSQL persistence, and a
clear separation of concerns.

## Features

- User registration and login with JWT authentication
- Secure endpoints with role-based access
- Refresh token and logout support
- PostgreSQL database with Flyway migrations
- Clean Architecture: domain, application, infrastructure, config
- Code quality enforced with Spotless and Checkstyle
- Docker Compose for local development
- API documentation via Swagger/OpenAPI

## Tech Stack

- Java 17+
- Spring Boot 3+
- Spring Security (JWT)
- PostgreSQL
- Flyway
- MapStruct
- Gradle
- Docker Compose

## How to Run Locally

### Prerequisites

- Docker & Docker Compose
- Java 17+
- Gradle (or use the included wrapper)

### 1. Start the Database Only

To run only the database (PostgreSQL) for local development or testing:

```
docker compose up postgres -d
```

This will start a PostgreSQL instance with a persistent volume and run the `init-db.sql` script.

### 2. Start the Full Stack (App + Database)

To run both the Spring Boot app and the database using Docker Compose:

```
docker compose up --build
```

### 3. Run the Application via Gradle or IntelliJ

- You can run the app using Gradle:

  ```
  ./gradlew bootRun
  ```

- Or use the provided IntelliJ run configuration (.run/EldorgApplication.run.xml) which sets the required environment
  variables for you.

## GitHub Workflow (CI)

This project includes a GitHub Actions workflow that runs all unit tests on every push and pull request to main/master.
The workflow will fail if any test fails, and will pass only if all tests succeed.

Workflow file: `.github/workflows/unit-tests.yml`

Key steps:

- Checks out the code
- Sets up JDK 21
- Runs all unit tests with Gradle
- Uploads test results as an artifact

You can view the workflow status and logs in the GitHub Actions tab of your repository.

## API Documentation

All API endpoints are documented and testable via Swagger UI. You do not need to rely on static API examples—simply use
the interactive documentation provided.

You can access Swagger UI at:

[http://localhost:8080/api/v1/swagger-ui.html](http://localhost:8080/api/v1/swagger-ui.html)

This interface allows you to explore, test, and understand all available endpoints, request/response formats, and
authentication requirements in real time.

## Project Structure

```
eldorg/
├── src/main/java/com/example/eldorg/
│   ├── api/           # Controllers (entrypoints)
│   ├── application/   # Use cases, DTOs
│   ├── config/        # App configuration
│   ├── domain/        # Models, repositories, services (business logic)
│   └── infrastructure/# Persistence, security, adapters
├── src/main/resources/
│   ├── application.yml
│   └── db/migration/  # Flyway migrations
├── docs/              # Architecture & feature docs
├── docker-compose.yml
├── init-db.sql
├── Makefile
├── README.md
└── .env.example
```

## Contribution Guide

See [docs/contributing.md](docs/contributing.md) for guidelines.

## API Documentation

All API endpoints are documented and testable via Swagger UI. You do not need to rely on static API examples—simply use
the interactive documentation provided.

You can access Swagger UI at:

[http://localhost:8080/api/v1/swagger-ui.html](http://localhost:8080/api/v1/swagger-ui.html)

This interface allows you to explore, test, and understand all available endpoints, request/response formats, and
authentication requirements in real time.
