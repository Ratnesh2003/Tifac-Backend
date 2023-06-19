FROM ubuntu:latest AS build

RUN apt-get update

RUN apt-get install openjdk-17-jdk -y

WORKDIR /app

COPY . .

# To allow the file to alter with tehe file we need to give the execution access to the mvn below such that it can run inside the container
RUN ./mvnw package

FROM openjdk:17-jdk-slim

EXPOSE 8080

COPY --from=build /app/target/tifac-1.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
