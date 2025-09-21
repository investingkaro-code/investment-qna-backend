# Use Java 17 for Spring Boot
FROM openjdk:17-jdk-slim

# Set working dir
WORKDIR /app

# Copy Maven wrapper & pom.xml first (for caching)
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Make sure wrapper is executable
RUN chmod +x ./mvnw

# Build application
COPY src src
RUN ./mvnw clean package -DskipTests

# Run the jar (finds the jar dynamically)
CMD ["sh", "-c", "java -jar target/*.jar"]

