# Core — Project Structure & Infrastructure

## Project Structure

```
/                         # Gradle root (settings.gradle.kts)
├── backend/              # Spring Boot 4 / Java 25 (Hexagonal Architecture)
│   ├── Dockerfile        # Multi-stage build (frontend + backend, dev + prod)
│   └── src/
│       ├── main/java/com/vangroenheesch/
│       └── test/java/com/vangroenheesch/
├── frontend/             # Angular 21 (standalone components, Signals, Vitest)
│   └── src/app/
├── docker-compose.yml    # Dev (backend + frontend) and prod services
└── .github/workflows/    # CI: ci.yml
```
---

## Infrastructure

### Docker (multi-stage build)
The `backend/Dockerfile` defines 6 stages: `frontend-dev`, `frontend-builder`, `base`, `dev`, `builder`, `prod`. The prod image bundles the Angular SPA as static files inside the Spring Boot jar, served by a resource handler fallback in `WebConfig`.

### Docker Compose
| Service | Target | Port | Purpose |
|---|---|---|---|
| `backend-dev` | `dev` | 8080 | Spring Boot with source volume-mounted |
| `frontend-dev` | `frontend-dev` | 4200 | Angular dev server (Bun), proxies `/api` to backend |
| `backend-prod` | `prod` | 8080 | Production image (profile: `prod`) |

### CI (GitHub Actions)
Single workflow **`ci.yml`** with three parallel jobs:
-   **`backend`** - Spotless -> JUnit testst -> Build
-   **`frontend`** - ESLint -> Prettier check -> Vitest -> Build
-   **`docker`** - Builds the prod docker image. Runs only on main, after backend + frontend pass
