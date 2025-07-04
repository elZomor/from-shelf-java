# Authentication & Security

## JWT Authentication

- On login, a JWT is issued and returned to the client.
- The JWT must be sent in the `Authorization: Bearer <token>` header for secured endpoints.
- The token is validated by a custom filter (`JwtAuthenticationFilter`).

## Registration & Login Flow

1. **Register**: `POST /api/v1/auth/register` creates a new user and returns a JWT.
2. **Login**: `POST /api/v1/auth/login` authenticates credentials and returns a JWT.
3. **Refresh**: `POST /api/v1/auth/refresh` issues a new JWT if the refresh token is valid.
4. **Logout**: `POST /api/v1/auth/logout` revokes the refresh token.

## Refresh Token & Logout

- Refresh tokens are tracked and can be revoked (see `AuthenticationService`).
- Logout revokes the refresh token, preventing further use.

## Security Configuration

- All `/api/v1/auth/**` endpoints are public (no authentication required).
- All other endpoints require a valid JWT.
- CSRF is disabled for stateless APIs.
- CORS can be configured in `SecurityConfig` if needed.

## Exception Handling

- Custom exceptions are handled by `GlobalExceptionHandler`.
- Unauthorized or forbidden access returns standard HTTP status codes.

