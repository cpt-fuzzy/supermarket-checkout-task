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