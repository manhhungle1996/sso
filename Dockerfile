FROM maven:3.6.3-jdk-11 as builder
COPY settings.xml "/root/.m2/settings.xml"
WORKDIR /app

COPY authen-lib/ /app/authen-lib/
COPY auth-service/ /app/auth-service/
RUN cd  authen-lib && mvn install -DskipTests=true
RUN cd  /app/auth-service/ && mvn install -DskipTests=true

FROM openjdk:11
WORKDIR /app
COPY application.properties /app/config/application.properties
COPY --from=builder /app/auth-service/target/*.jar /app.jar
ENTRYPOINT ["java", "-jar", "/app.jar -Dspring.config.location=/app/config/application.properties"]
