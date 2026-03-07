# Supermarket Checkout

A full-stack supermarket checkout system.

## Tech Stack

### Backend

| Technology | Version | Purpose |
|---|---|---|
| Java | 25 (LTS) | Language |
| Spring Boot | 4.0.3 | Web framework, dependency injection, auto-configuration |
| Spring Data JPA | 4.0.3 | Database access via repository interfaces |
| H2 | 2.4 | In-memory database (development and testing) |
| Hibernate | 7.2 | JPA implementation |
| Gradle | 9.3.1 | Build tool (wrapper included, no local install needed) |
| JUnit 5 | | Unit and integration testing |
| ArchUnit | 1.4.1 | Architecture rule enforcement |
| Spotless | 8.3.0 | Code formatting (google-java-format) |
| Error Prone | 2.43.0 | Compile-time bug detection |

### Frontend

| Technology | Version | Purpose |
|---|---|---|
| Angular | 21.2 | UI framework (standalone components, signals, OnPush) |
| TypeScript | 5.9 | Language |
| Tailwind CSS | 4.1 | Utility-first styling |
| Vitest | 4.0 | Unit testing |

### Infrastructure

| Technology | Purpose |
|---|---|
| Docker | Multi-stage builds for dev and prod |
| Docker Compose | Local development and production orchestration |
| GitHub Actions | CI pipeline (formatting, tests, build) |

## Architecture

The backend follows **Clean Architecture + DDD + Hexagonal (Ports & Adapters)**. Dependencies point inward: Infrastructure -> Application -> Domain. The domain layer has zero external dependencies.

```
backend/src/main/java/com/vangroenheesch/
├── domain/
│   ├── model/       # Pure Java records (Product, Cart, Offer, Receipt, ...)
│   ├── port/        # Driven port interfaces (ProductRepositoryPort, OfferRepositoryPort)
│   └── service/     # Domain service (PricingService — pure calculation, no I/O)
├── application/     # Use cases + handlers (orchestrate ports and domain service)
└── infrastructure/
    ├── persistence/  # JPA entities, Spring Data repos, port adapters
    ├── web/          # REST controllers, DTOs, exception handler
    └── config/       # CORS, data seeding
```

## Prerequisites

- **Docker** and **Docker Compose** (for containerized development)
- **JDK 25** (only if running outside Docker — Gradle auto-provisions via foojay toolchain resolver)
- **Node.js 22+** and **bun** (for frontend development)

## Running the Application

### With Docker (recommended)

```sh
# Build and start the backend in dev mode
docker compose up backend-dev

# The app is available at http://localhost:8080
# Restart the container to pick up source code changes:
docker compose restart backend-dev
```

### With Docker (production build)

```sh
# Build and run the optimized production image
docker compose --profile prod up backend-prod
```

### Without Docker

```sh
# Backend
./gradlew :backend:bootRun

# Frontend (in a separate terminal)
cd frontend
bun install
bun run start
# Available at http://localhost:4200
```

## Build Commands

### Backend

| Command | Purpose |
|---|---|
| `./gradlew :backend:build` | Full build (compile + test + package) |
| `./gradlew :backend:test` | Run all tests |
| `./gradlew :backend:test --tests "com.vangroenheesch.SomeTest"` | Run a single test class |
| `./gradlew :backend:spotlessApply` | Format all Java files |
| `./gradlew :backend:spotlessCheck` | Check formatting (fails if unformatted) |
| `./gradlew :backend:bootRun` | Start the dev server |

### Frontend

| Command         | Purpose |
|-----------------|---|
| `bun install`   | Install dependencies |
| `bun run start` | Start the dev server (`http://localhost:4200`) |
| `bun run test`  | Run all tests |
| `bun run build` | Production build |

## API

| Endpoint | Method | Description |
|---|---|---|
| `/api/products` | GET | List all products with active offers |
| `/api/checkout` | POST | Calculate receipt for a cart |

### Checkout request

```json
{
  "items": [
    { "productSku": "apple", "quantity": 3 },
    { "productSku": "banana", "quantity": 2 }
  ]
}
```

### Checkout response

```json
{
  "lines": [
    {
      "productName": "Apple",
      "quantity": 3,
      "unitPrice": 0.30,
      "lineTotal": 0.75,
      "offerDescription": "2 for 0.45",
      "lineSaved": 0.15
    },
    {
      "productName": "Banana",
      "quantity": 2,
      "unitPrice": 0.50,
      "lineTotal": 1.00,
      "offerDescription": null,
      "lineSaved": 0.00
    }
  ],
  "total": 1.75,
  "saved": 0.15
}
```

## Seed Data

| SKU | Name | Price | Offer |
|---|---|---|---|
| apple | Apple | 0.30 | 2 for 0.45 |
| banana | Banana | 0.50 | 3 for 1.00 |
| orange | Orange | 0.60 | — |
| milk | Milk | 1.20 | — |
| bread | Bread | 0.80 | 2 for 1.20 |
