# Database Design & Migrations

## Entity & Table Design

- User and related entities are defined in the `domain/model` package.
- Each entity maps to a table in PostgreSQL.

## Flyway Usage

- Database migrations are managed with Flyway.
- Migration scripts are located in `src/main/resources/db/migration/`.
- The initial schema is created by `V1__init.sql`.

## How to Apply Migrations

1. Ensure PostgreSQL is running (see `docker-compose.yml`).
2. Run migrations with Gradle:
   ```
   ./gradlew flywayMigrate
   ```
3. Flyway will apply any new migration scripts in order.

## Migration Structure

- Each migration file is named `V<version>__<description>.sql`.
- Example: `V1__init.sql` creates tables and roles.

## Tips

- Never edit applied migration files. Create a new version for changes.
- Use `flywayInfo` to check migration status.

