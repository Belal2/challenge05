FROM openjdk:21-jdk-slim

COPY target/challenge05*.jar /app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]