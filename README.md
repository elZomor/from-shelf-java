# Eldorg Project

## Overview

Eldorg is a Java Spring Boot application following Clean Architecture principles. The project is structured for
maintainability, testability, and scalability, with clear separation of concerns across domain, application, API, and
infrastructure layers.

## Project Structure

- `config/` — Application configuration classes
- `domain/` — Business logic, models, repositories, and services
- `application/` — Use cases and DTOs
- `api/` — REST controllers
- `infrastructure/` — Persistence and security implementations
- `docs/` — Documentation and diagrams

## Prerequisites

- Java 21+
- Gradle 8+
- Docker & Docker Compose

## Database Setup

The project uses PostgreSQL as its database. The recommended way to run the database is via Docker Compose, which
ensures a consistent and isolated environment.

### 1. Start PostgreSQL with Docker Compose

Run the following command in the project root:

```
docker-compose up -d
```

This will:

- Start a PostgreSQL container
- Mount a persistent volume for data
- Run the `init-db.sql` script to initialize the database if it does not exist

### 2. Database Initialization

The `init-db.sql` script is automatically executed by Docker Compose on first run. It:

- Creates the database if it does not exist
- Sets the owner role

You can find and modify this script at the project root: `init-db.sql`.

### 3. Database Credentials

The credentials and database name are defined in both `docker-compose.yml` and referenced in the Spring Boot
`application.yml` file. Make sure these match if you change them.

- **Database:** `eldorg_db`
- **User:** `eldorg_user`
- **Password:** `eldorg_pass`

## Running the Application

1. Ensure the database is running (see above).
2. Build and run the Spring Boot application:

```
./gradlew bootRun
```

## Code Quality

- **Spotless** is used for code formatting (Google Java Format)
- **Checkstyle** is used for linting (Google Java Style Guide)

You can use the provided Makefile for common tasks:

- `make format` — Format code
- `make lint` — Run Checkstyle
- `make checkall` — Run both

## Documentation

- All documentation and diagrams are in the `docs/` directory.
- The domain model is described in `domain-model.puml` (PlantUML format).

## Clean Architecture

The project is organized according to Clean Architecture principles. See the `docs/` directory for diagrams and further
explanation.

## Contact

For questions or contributions, please refer to the documentation or open an issue.

