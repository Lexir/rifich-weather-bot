FROM bellsoft/liberica-openjdk-alpine:21 AS build

WORKDIR /build

COPY gradlew build.gradle settings.gradle ./
COPY gradle gradle
RUN chmod +x gradlew
RUN ./gradlew --no-daemon dependencies

COPY src src

RUN ./gradlew --no-daemon clean bootJar

FROM  bellsoft/liberica-runtime-container:jdk-crac-cds-slim

WORKDIR /app

COPY --from=build /build/build/libs/*.jar app.jar

EXPOSE 8080

CMD ["java", "-XX:MaxRAMPercentage=75", "-jar", "app.jar"]
