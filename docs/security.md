# Security Details

## Filters Used

- **JwtAuthenticationFilter**: Validates JWT tokens for secured endpoints. Skipped for `/api/v1/auth/**`.
- **Spring Security Filter Chain**: Configured in `SecurityConfig`.

## Exception Handling

- **GlobalExceptionHandler**: Handles custom and generic exceptions, returning appropriate HTTP status codes.
- **Spring Security**: Returns 401/403 for unauthorized/forbidden access.

## CORS & CSRF

- **CSRF**: Disabled for stateless JWT APIs.
- **CORS**: Can be configured in `SecurityConfig` if cross-origin requests are needed.

## Token Validation

- JWTs are validated for signature, expiration, and user details.
- Refresh tokens are tracked and can be revoked on logout.

## Security Best Practices

- Only `/api/v1/auth/**` is public; all other endpoints require authentication.
- Passwords are hashed using BCrypt.
- Sensitive configuration is externalized via environment variables.

