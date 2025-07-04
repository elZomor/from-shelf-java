# API Reference

## Public Endpoints

### Register

- **POST** `/api/v1/auth/register`
- **Body:**

```json
{
  "username": "user1",
  "password": "pass123"
}
```

- **Response:**

```json
{
  "data": { "token": "<JWT>" },
  "status": 200
}
```

### Login

- **POST** `/api/v1/auth/login`
- **Body:**

```json
{
  "username": "user1",
  "password": "pass123"
}
```

- **Response:**

```json
{
  "data": { "token": "<JWT>" },
  "status": 200
}
```

### Refresh Token

- **POST** `/api/v1/auth/refresh-token`
- **Body:**

```json
{
  "refreshToken": "<REFRESH_TOKEN>"
}
```

- **Response:**

```json
{
  "data": { "token": "<JWT>" },
  "status": 200
}
```

### Logout

- **POST** `/api/v1/auth/logout`
- **Body:**

```json
{
  "refreshToken": "<REFRESH_TOKEN>"
}
```

- **Response:**

```json
{
  "data": null,
  "status": 200
}
```

## Script Endpoints

### Get All Approved Scripts

- **GET** `/api/v1/scripts`
- **Response:**

```json
{
  "data": [
    {
      "id": 1,
      "name": "Script 1",
      "status": "APPROVED"
    }
  ],
  "status": 200
}
```

### Get Approved Script by ID

- **GET** `/api/v1/scripts/{id}`
- **Response:**

```json
{
  "data": {
    "id": 1,
    "name": "Script 1",
    "status": "APPROVED"
  },
  "status": 200
}
```

## Error Responses

- 401 Unauthorized: Invalid or missing token
- 403 Forbidden: Insufficient permissions
- 400 Bad Request: Validation errors

See Swagger UI for full details: `/api/v1/swagger-ui.html`
