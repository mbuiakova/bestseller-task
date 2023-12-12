FROM maven:3.9.6-sapmachine-21 AS builder
LABEL authors="Maria Buiakova"

WORKDIR /usr/src/app
COPY pom.xml /usr/src/app
COPY src /usr/src/app/src

RUN mvn compile package spring-boot:repackage

FROM openjdk:21-jdk
LABEL authors="Maria Buiakova"

COPY --from=builder /usr/src/app/target/bestseller-0.0.1-SNAPSHOT.jar /app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]