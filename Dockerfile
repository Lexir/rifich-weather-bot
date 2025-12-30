FROM bellsoft/liberica-openjdk-alpine:21 AS build

WORKDIR /build

COPY gradlew build.gradle settings.gradle ./
COPY gradle gradle

RUN ./gradlew --no-daemon dependencies

COPY src src

RUN ./gradlew --no-daemon clean bootJar

FROM bellsoft/liberica-runtime-container:jre-21

WORKDIR /app
EXPOSE 8080

USER 1001

COPY --from=build /build/build/libs/*.jar app.jar

ENTRYPOINT ["java", \
  "-XX:MaxRAMPercentage=75", \
  "-XX:+UseG1G
