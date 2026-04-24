# Appointment Booking API Project

This project was created during an internal training organized by my employer.

## Table of Contents

- [Java Version](#java-version)
- [Used Libraries](#used-libraries)
- [Available Endpoints](#available-endpoints)

## Java Version

This project uses Java version: **21**

## Used Libraries

List of libraries used in this project along with their versions:

- spring-boot-starter-web: 3.5.13
- spring-boot-starter-data-jpa: 3.5.13
- spring-boot-starter-security: 3.5.13
- spring-boot-starter-validation: 3.5.13
- spring-boot-starter-actuator: 3.5.13
- springdoc-openapi-starter-webmvc-ui: 2.8.5
- openapi-generator-maven-plugin: 7.11.0
- swagger-annotations: 2.2.28
- jackson-databind-nullable: 0.2.6
- mapstruct: 1.5.5.Final
- querydsl-jpa: 5.1.0
- flyway-core (managed by Spring Boot)
- h2 (managed by Spring Boot)
- lombok: 1.18.30
- spring-boot-starter-test: 3.5.13
- spring-security-test: 3.5.13
- junit-jupiter-api: 5.10.2

## Available Endpoints

### List treatments

- **URL:** `/api/v1/treatments`
- **Method:** `GET`
- **Auth required:** Yes (Basic Auth)

#### Success Response

- **Code:** `200 OK`
- **Content:**

```json
[
    {
        "id": -1,
        "name": "Konsultacja dentystyczna",
        "duration": 30,
        "specialistId": -1
    }
]
```

### Get treatment details

- **URL:** `/api/v1/treatments/{id}`
- **Method:** `GET`
- **Auth required:** Yes (Basic Auth)

#### Success Response

- **Code:** `200 OK`
- **Content:**

```json
{
    "id": -1,
    "name": "Konsultacja dentystyczna",
    "description": "Konsultacja dentystyczna z diagnostyką i planem leczenia",
    "duration": 30,
    "specialist": {
        "id": -1,
        "name": "DENTIST"
    }
}
```

#### Error Response

- **Code:** `404 NOT FOUND`
- **Content:**

```json
{
    "status": "404",
    "message": "Treatment has not been found"
}
```

### Create treatment

- **URL:** `/api/v1/treatments`
- **Method:** `POST`
- **Auth required:** Yes (Basic Auth, role `SPECIALIST`)
- **Body:**

```json
{
    "name": "Konsultacja dentystyczna",
    "description": "Konsultacja dentystyczna z diagnostyką i planem leczenia",
    "duration": 30,
    "specialistId": -1
}
```

### List appointments

- **URL:** `/api/v1/appointments`
- **Method:** `GET`
- **Auth required:** Yes (Basic Auth)

#### Success Response

- **Code:** `200 OK`
- **Content:**

```json
[
    {
        "id": -1,
        "clientId": -1,
        "treatmentId": -1,
        "dateTime": "2024-03-01T09:00:00Z",
        "status": "SCHEDULED"
    }
]
```

### Book a new appointment

- **URL:** `/api/v1/appointments`
- **Method:** `POST`
- **Auth required:** Yes (Basic Auth)
- **Body:**

```json
{
    "clientId": -1,
    "treatmentId": -1,
    "dateTime": "2024-06-01T09:00:00Z"
}
```

#### Error Response

- **Code:** `400 BAD REQUEST`
- **Content:**

```json
{
    "status": "400",
    "message": "Specialist is not available at this time"
}
```

### Update appointment status (cancel / complete)

- **URL:** `/api/v1/appointments/{id}`
- **Method:** `PATCH`
- **Auth required:** Yes (Basic Auth)
- **Body:**

```json
{
    "status": "CANCELLED"
}
```

### Current authenticated user info

- **URL:** `/api/user/info`
- **Method:** `GET`
- **Auth required:** Yes (Basic Auth)

#### Success Response

- **Code:** `200 OK`
- **Content:**

```json
{
    "authenticated": true,
    "username": "client",
    "roles": ["CLIENT"],
    "id": -1,
    "clientId": -1
}
```

All responses are in `application/json` format.
