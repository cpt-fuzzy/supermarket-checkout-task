FROM oven/bun:1.3 AS frontend-dev
WORKDIR /app/frontend
COPY frontend/package.json frontend/bun.lock ./
RUN bun install --frozen-lockfile
EXPOSE 4200
CMD ["bun", "run", "ng", "serve", "--host", "0.0.0.0", "--proxy-config", "proxy.conf.docker.json"]

FROM frontend-dev AS frontend-builder
COPY frontend/ .
RUN bun run build

FROM eclipse-temurin:25-jdk AS base
WORKDIR /app
COPY gradlew gradlew
COPY gradle/ gradle/
COPY settings.gradle.kts settings.gradle.kts
COPY backend/build.gradle.kts backend/build.gradle.kts
RUN chmod +x gradlew

FROM base AS dev
RUN ./gradlew :backend:dependencies --no-daemon
EXPOSE 8080
CMD ["./gradlew", ":backend:bootRun", "--no-daemon"]

FROM base AS build
COPY backend/src/ backend/src/
COPY --from=frontend /app/frontend/dist/frontend/browser backend/src/main/resources/static
RUN ./gradlew :backend:bootJar --no-daemon
RUN java -Djarmode=tools -jar $(ls backend/build/libs/*.jar | grep -v plain) extract --destination backend/extracted

FROM eclipse-temurin:25-jre-alpine AS production
RUN addgroup --system app && adduser --system --ingroup app appuser
WORKDIR /app
COPY --from=build /app/backend/extracted/lib/ ./lib/
COPY --from=build /app/backend/extracted/*.jar ./app.jar
USER appuser
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]