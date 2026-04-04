FROM gradle:9.0.0-jdk21 AS build

WORKDIR /erp-backend

COPY build.gradle .
COPY settings.gradle .

RUN gradle wrapper
RUN ./gradlew dependencies --no-daemon

COPY src src
RUN ./gradlew bootJar --no-daemon -x test

FROM eclipse-temurin:21-jre

WORKDIR /app

COPY --from=build /erp-backend/build/libs/*.jar app.jar

EXPOSE 8085

ENTRYPOINT ["java", "-jar", "app.jar"]