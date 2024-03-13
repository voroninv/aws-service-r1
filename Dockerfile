#Stage 1: Build
FROM maven:3.8.4-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean install

#Stage 2: Run
FROM openjdk:17-alpine
WORKDIR /app
COPY --from=build /app/target/aws-service-r1-0.0.1-SNAPSHOT.jar ./aws-service-r1-0.0.1-SNAPSHOT.jar
EXPOSE 8080
CMD ["java", "-jar", "aws-service-r1-0.0.1-SNAPSHOT.jar"]