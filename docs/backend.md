# Backend — Java / Spring Boot

## Architecture — Hexagonal (Ports & Adapters)
- Domain model lives in `domain/model/` — pure Java records or value objects, no framework annotations.
- Output ports (repository interfaces) live in `domain/port/`.
- Adapters (JPA, REST controllers) live outside the domain layer.
- Domain objects must not import Spring or JPA classes.

## Java Style
- Use **Java records** for immutable value objects and domain models.
- Validate invariants in the compact constructor using `IllegalArgumentException`.
- Use `Optional<T>` for nullable return types from repository ports.
- Use `BigDecimal` for monetary values — never `double` or `float`.
- Prefer package-private visibility; only expose what is needed.
- No Lombok; records replace most boilerplate.

## Naming
- Classes: `PascalCase`. Methods/variables: `camelCase`. Constants: `UPPER_SNAKE_CASE`.
- Port interfaces: suffix with `Port` (e.g., `ProductRepositoryPort`).
- Test classes: suffix with `Tests` (e.g., `DemoApplicationTests`).

## Testing (JUnit 5 + Spring Boot Test)
- Use `@SpringBootTest` for integration/context-load tests.
- Prefer unit tests for domain logic — no Spring context needed for pure records.
- Use `@Test` from `org.junit.jupiter.api.Test`.
