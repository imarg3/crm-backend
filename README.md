# CRM Backend

A CRM backend built with **Spring Boot 3.3** and **Java 21**, targeting travel-agency use cases: customer management, lead/booking tracking with travel details, and optional AI-powered lead assistance.

---

## Table of Contents

- [Tech Stack](#tech-stack)
- [Project Structure](#project-structure)
- [Prerequisites](#prerequisites)
- [Local Setup](#local-setup)
- [Environment Variables](#environment-variables)
- [Running the Application](#running-the-application)
- [API Reference](#api-reference)
- [Database](#database)
- [Testing](#testing)
- [Docker / Podman](#docker--podman)
- [Cloud Deployment (Render)](#cloud-deployment-render)
- [Troubleshooting](#troubleshooting)

---

## Tech Stack

| Layer | Technology |
|---|---|
| Language | Java 21 (Records, Pattern Matching, Virtual Threads) |
| Framework | Spring Boot 3.3.0 |
| Security | Spring Security + JWT (jjwt 0.12.5) |
| Database | PostgreSQL 16 |
| Migrations | Liquibase 4.27 |
| ORM | Spring Data JPA / Hibernate |
| DTO Mapping | MapStruct 1.5.5 |
| Caching | Caffeine |
| Validation | Jakarta Bean Validation + Passay (password rules) |
| API Docs | SpringDoc OpenAPI (Swagger UI) |
| Monitoring | Spring Boot Actuator |
| Logging | Logback + Logstash JSON encoder |
| AI (optional) | Spring AI 1.0 — OpenAI (`spring-ai-starter-model-openai`) by default, switchable |
| Build | Maven (mvnw wrapper included) |
| Container | Podman / Docker |

---

## Project Structure

```
crm-backend/
├── src/
│   ├── main/
│   │   ├── java/org/code/bluetick/
│   │   │   ├── CrmApplication.java           # Main entry point
│   │   │   ├── ai/                           # AI feature (feature-flagged)
│   │   │   │   ├── config/                   # AIFeatureProperties
│   │   │   │   ├── provider/                 # AIModelProvider abstraction
│   │   │   │   ├── service/                  # LeadAIService (summarize, email, itinerary)
│   │   │   │   └── web/                      # AIController + DTOs
│   │   │   ├── config/                       # App configs (JWT, Mail, Cache, OpenAPI, Tx)
│   │   │   ├── enums/                        # Domain enums (Destination, Services, LeadStatus…)
│   │   │   ├── persistence/
│   │   │   │   ├── model/                    # JPA entities (Customer, Lead, User, TravelDetail…)
│   │   │   │   └── repository/               # Spring Data repositories
│   │   │   ├── registration/                 # Registration event + listener (email verification)
│   │   │   ├── security/                     # JWT filter, AuthEntryPoint, SecurityConfig
│   │   │   ├── service/                      # Business logic (CustomerService, LeadService…)
│   │   │   ├── utils/                        # LeadUtils (lead ID generation)
│   │   │   ├── validation/                   # Custom validators (email, password)
│   │   │   └── web/
│   │   │       ├── controller/               # REST controllers (Auth, Customer, Lead)
│   │   │       ├── exception/                # Global exception handler + custom exceptions
│   │   │       ├── mapstruct/                # MapStruct mapper + request/response DTOs
│   │   │       ├── payload/                  # GenericResponse, JwtResponse
│   │   │       └── requests/                 # Request ID logging aspect
│   │   └── resources/
│   │       ├── application.properties        # Common config
│   │       ├── application-dev.properties    # Dev profile (DB, JWT, Mail, AI defaults)
│   │       ├── application-prod.properties   # Prod profile
│   │       ├── logback-spring.xml            # Structured JSON logging
│   │       ├── mail.properties               # Mail template config
│   │       ├── openapi-spec.yml              # OpenAPI spec
│   │       └── db/changelog/                 # Liquibase changesets
│   │           ├── db.changelog-master.yaml
│   │           ├── db.changelog-baseline.yaml
│   │           └── migrations/v1.1/          # Lead notes, activities, assignment
│   └── test/                                 # Integration tests (Testcontainers)
├── .env.development                          # Local env vars (not committed)
├── podman-compose.yml                        # PostgreSQL container for local dev
├── init-db.sql                               # DB init script (schema + seed data)
├── start-dev.sh                              # Dev startup helper
├── Dockerfile                                # Production container image
└── pom.xml
```

---

## Prerequisites

- **Java 21** — `java -version` should show `21.x`
- **Maven** — the `mvnw` wrapper is included, no separate install needed
- **Podman** or **Docker** — for running PostgreSQL locally
- **podman-compose** or **docker compose** — for the compose file

---

## Local Setup

### 1. Clone and enter the repo

```bash
git clone <repo-url>
cd crm-backend
```

### 2. Start the database

```bash
podman-compose up -d
# or: docker compose up -d
```

This starts a PostgreSQL 16 container (`crm-postgres`) on port `5432` and runs `init-db.sql` to create the `crm` schema, tables, sequences, enum types, and seed role data.

### 3. Configure environment variables

Create `.env.development` in the project root (already gitignored):

```bash
# Database
SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/crmdb
SPRING_DATASOURCE_USERNAME=crmuser
SPRING_DATASOURCE_PASSWORD=crmpass123

# JWT (development-only secret — change for production)
JWT_SECRET=dGVzdC1zZWNyZXQtZm9yLWRldmVsb3BtZW50LW9ubHktZG8tbm90LXVzZS1pbi1wcm9kdWN0aW9u
JWT_EXPIRATION=86400000

# Mail (optional for local testing — set to valid credentials to test email)
MAIL_HOST=smtp.gmail.com
MAIL_PORT=587
MAIL_USERNAME=your-email@gmail.com
MAIL_PASSWORD=your-app-password

# Spring Profile
SPRING_PROFILES_ACTIVE=dev

# AI (optional — set AI_ENABLED=true and provide a key to test AI endpoints)
AI_ENABLED=false
OPENAI_API_KEY=
```

### 4. Start the application

```bash
# Option A — use the included startup script (loads .env.development automatically)
./start-dev.sh

# Option B — load env manually then run
source .env.development
./mvnw spring-boot:run
```

### 5. Verify it is running

| Resource | URL |
|---|---|
| Health check | http://localhost:8080/actuator/health |
| Swagger UI | http://localhost:8080/swagger-ui-custom.html |
| OpenAPI JSON | http://localhost:8080/api-docs |

---

## Environment Variables

| Variable | Default (dev) | Description |
|---|---|---|
| `SPRING_DATASOURCE_URL` | `jdbc:postgresql://localhost:5432/crmdb` | JDBC URL |
| `SPRING_DATASOURCE_USERNAME` | `crmuser` | DB username |
| `SPRING_DATASOURCE_PASSWORD` | `crmpass123` | DB password |
| `JWT_SECRET` | (base64 dev secret) | HMAC secret for JWT signing |
| `JWT_EXPIRATION` | `86400000` | Token TTL in milliseconds (24 h) |
| `MAIL_HOST` | `smtp.gmail.com` | SMTP host |
| `MAIL_PORT` | `587` | SMTP port |
| `MAIL_USERNAME` | — | SMTP username / sender address |
| `MAIL_PASSWORD` | — | SMTP password / app password |
| `SPRING_PROFILES_ACTIVE` | `dev` | Active Spring profile (`dev` or `prod`) |
| `AI_ENABLED` | `false` | Enable AI endpoints (`true`/`false`) |
| `OPENAI_API_KEY` | — | OpenAI API key (required if AI enabled) |
| `AI_MODEL` | `gpt-4o-mini` | OpenAI model name |

---

## API Reference

All endpoints are prefixed with `/api/v1`. Protected endpoints require a `Bearer <token>` header obtained from sign-in.

### Authentication

No authentication required.

| Method | Path | Description |
|---|---|---|
| `POST` | `/auth/sign-up` | Register a new user account |
| `POST` | `/auth/sign-in` | Sign in and obtain a JWT token |

**Sign-up body**

```json
{
  "fullName": "Arpit Gupta",
  "businessName": "Travel Co",
  "email": "arpit@example.com",
  "password": "MyPass@123",
  "matchingPassword": "MyPass@123",
  "mobile": "+919876543210",
  "country": "India",
  "state": "Maharashtra",
  "city": "Mumbai"
}
```

Password rules: minimum 8 characters, at least one uppercase, one lowercase, one digit, one special character.

**Sign-in body**

```json
{
  "usernameOrEmail": "arpit@example.com",
  "password": "MyPass@123"
}
```

**Sign-in response** — the `token` field is the Bearer token to use for all subsequent requests.

```json
{
  "status": "SUCCESS",
  "message": "Authentication successful",
  "data": {
    "token": "<jwt>",
    "email": "arpit@example.com",
    "roles": ["ROLE_AGENT"]
  }
}
```

---

### Customers

Requires `Authorization: Bearer <token>`.

| Method | Path | Description |
|---|---|---|
| `GET` | `/customers` | List all customers (pageable) |
| `POST` | `/customers` | Create a new customer |
| `GET` | `/customers/{emailId}` | Get customer by email |
| `PUT` | `/customers/{emailId}` | Update customer |
| `DELETE` | `/customers/{id}` | Delete customer by numeric ID |

Pagination parameters (query string): `page=0&size=20&sort=name,asc`

**Create / update customer body**

```json
{
  "name": "John Doe",
  "email": "john.doe@example.com",
  "mobile": "+919876543210",
  "birthDate": "1990-05-15"
}
```

---

### Leads

Requires `Authorization: Bearer <token>`.

| Method | Path | Description |
|---|---|---|
| `GET` | `/leads` | List all leads (pageable) |
| `POST` | `/leads` | Create a new lead |
| `GET` | `/leads/{leadId}` | Get lead by lead ID (e.g. `CRM-ABCDE-001`) |

**Create lead body**

```json
{
  "customer": {
    "name": "John Doe",
    "email": "john.doe@example.com",
    "mobile": "+919876543210",
    "birthDate": "1990-05-15"
  },
  "travelDetail": {
    "departureCity": "Mumbai",
    "nationality": "Indian",
    "travelDate": "2026-09-15",
    "rooms": 2,
    "totalNights": 5,
    "destinations": ["Dubai", "Singapore"],
    "travellers": [
      { "name": "John Doe", "age": 35, "personType": "Adult" },
      { "name": "Jane Doe", "age": 30, "personType": "Adult" },
      { "name": "Tom Doe",  "age":  8, "personType": "Child" }
    ]
  },
  "services": ["Stays", "Flights", "Transfers", "Sightseeing"]
}
```

**Valid values**

| Field | Allowed values |
|---|---|
| `destinations` | `Dubai`, `Singapore`, `Malaysia`, `Thailand`, `Bali` |
| `services` | `Stays`, `Flights`, `Transfers`, `Sightseeing`, `Cruise`, `Visa`, `Insurance`, `Ferry`, `Train`, `Bus` |
| `personType` | `Adult`, `Child` |

---

### AI Endpoints (optional)

Requires `Authorization: Bearer <token>` and `feature.ai.enabled=true` (set `AI_ENABLED=true` with a valid API key).

| Method | Path | Description |
|---|---|---|
| `POST` | `/ai/leads/{leadId}/summary` | Generate a natural-language summary of a lead |
| `POST` | `/ai/leads/{leadId}/email-draft` | Draft a follow-up email for a lead |
| `POST` | `/ai/leads/{leadId}/itinerary` | Generate a travel itinerary for a lead |

---

## Database

### Connection (local)

```
Host:     localhost
Port:     5432
Database: crmdb
Username: crmuser
Password: crmpass123
Schema:   crm
```

### Migrations

Liquibase runs automatically on application startup. Changesets live in `src/main/resources/db/changelog/`.

```bash
# Run migrations manually
./mvnw liquibase:update

# View pending changesets
./mvnw liquibase:status
```

### Useful database commands

```bash
# Open psql shell in the container
podman exec -it crm-postgres psql -U crmuser -d crmdb

# List tables in crm schema
\dt crm.*

# Check migration history
SELECT id, author, dateexecuted FROM crm.databasechangelog ORDER BY orderexecuted;
```

---

## Testing

```bash
# Run all tests (uses Testcontainers — Docker/Podman must be running)
./mvnw test

# Run with coverage report
./mvnw clean test jacoco:report
# Report: target/site/jacoco/index.html
```

Tests use Testcontainers to spin up a real PostgreSQL instance, so no manual DB setup is needed for tests.

---

## Docker / Podman

### Local development (database only)

```bash
podman-compose up -d      # start
podman-compose down       # stop
podman logs crm-postgres  # view logs
```

### Build the application image

```bash
podman build -t crm-backend:latest .
```

### Run the full stack via container

```bash
podman run -d \
  -p 8080:8080 \
  -e SPRING_PROFILES_ACTIVE=prod \
  -e SPRING_DATASOURCE_URL=jdbc:postgresql://db-host:5432/crmdb \
  -e SPRING_DATASOURCE_USERNAME=crmuser \
  -e SPRING_DATASOURCE_PASSWORD=changeme \
  -e JWT_SECRET=your-strong-secret \
  --name crm-backend \
  crm-backend:latest
```

---

## Cloud Deployment (Render)

1. Create a **PostgreSQL** database service on Render.
2. Create a **Web Service** pointing to this repository — Render auto-detects the `Dockerfile`.
3. Set environment variables in the Render dashboard (see [Environment Variables](#environment-variables)).
4. Push to `main`; Render deploys automatically.

Key production environment variables to set on Render:

```
SPRING_PROFILES_ACTIVE=prod
SPRING_DATASOURCE_URL=<render-internal-db-url>
SPRING_DATASOURCE_USERNAME=<render-db-user>
SPRING_DATASOURCE_PASSWORD=<render-db-password>
JWT_SECRET=<strong-random-secret-min-32-chars>
MAIL_USERNAME=<your-email>
MAIL_PASSWORD=<your-app-password>
```

---

## Troubleshooting

**Application won't start**

- Verify Java version: `java -version` (must be 21.x)
- Confirm the database container is up: `podman ps | grep postgres`
- Check environment variables are exported: `echo $SPRING_DATASOURCE_URL`

**Database connection failed**

- Ensure the container is healthy: `podman inspect crm-postgres | grep -A5 Health`
- Test the connection directly: `psql -h localhost -U crmuser -d crmdb`

**Liquibase errors on startup**

- If schema/tables already exist outside Liquibase control, check `crm.databasechangelog` and ensure baseline changeset is marked executed.

**Authentication errors**

- Confirm `JWT_SECRET` is set and consistent across restarts.
- Tokens expire after `JWT_EXPIRATION` ms (default 24 h) — sign in again if expired.

**AI endpoints return 404**

- Set `AI_ENABLED=true` (or `feature.ai.enabled=true` in properties) and provide a valid `OPENAI_API_KEY`.

---

## Author

**Arpit Gupta** — gupta.arpit03@gmail.com
