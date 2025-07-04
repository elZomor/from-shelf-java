# Clean Architecture Overview

This project follows Clean Architecture principles to ensure separation of concerns, testability, and maintainability.

## Layer Responsibilities

- **Domain**: Core business logic, entities, and interfaces (model, repository, service).
- **Application**: Use cases and DTOs. Orchestrates domain logic for specific operations.
- **API**: Controllers (entrypoints) that handle HTTP requests and responses.
- **Infrastructure**: Technical details (persistence, security, adapters).
- **Config**: Application and framework configuration (e.g., security, beans).

## Example Flow: Controller to Repository

1. **API Layer**: Receives HTTP request in a controller.
2. **Application Layer**: Controller calls a use case/service, passing DTOs.
3. **Domain Layer**: Use case invokes domain services/repositories for business logic.
4. **Infrastructure Layer**: Repository implementation interacts with the database.
5. **Response**: Data flows back up to the controller, which returns an HTTP response.

```
[Controller] → [UseCase/Service] → [Domain Model/Repository] → [Persistence]
```

See the `docs/database.md` and `docs/auth.md` for more details on data and authentication flows.

