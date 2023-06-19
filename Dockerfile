FROM maven:3.6.0-jdk-8 AS build

WORKDIR /app

COPY . .

RUN mvn package

FROM openjdk:17-jdk-slim

EXPOSE 8080

COPY --from=build /app/target/tifac-1.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]