# Use OpenJDK 17
FROM openjdk:17-jdk-slim

# Set working directory
WORKDIR /app

# Copy Maven wrapper & pom.xml
COPY mvnw* pom.xml ./
COPY .mvn .mvn

# Download dependencies
RUN ./mvnw dependency:go-offline -B

# Copy source code
COPY src src

# Build app
RUN ./mvnw clean package -DskipTests

# Run the app
CMD ["java", "-jar", "target/investment_qna-0.0.1-SNAPSHOT.jar"]

