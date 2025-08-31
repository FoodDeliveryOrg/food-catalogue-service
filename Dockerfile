FROM eclipse-temurin:24-jdk AS builder
WORKDIR /workspace

# Copy the Spring Boot fat jar from target folder
COPY target/*.jar app.jar

# Allow passing JVM options at runtime
ENTRYPOINT exec java $JAVA_OPTS -jar app.jar