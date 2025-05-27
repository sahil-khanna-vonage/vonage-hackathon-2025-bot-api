# Use a Maven image with Java 21
FROM maven:3.9.6-eclipse-temurin-21 AS build

# Set working directory
WORKDIR /app

# Copy the Maven build file and install dependencies
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy the application source code
COPY src ./src

# Build the application
RUN mvn clean package -DskipTests

# Use a lightweight Java runtime for production
FROM eclipse-temurin:21-jdk

WORKDIR /app

# Copy the .prod.env file into the container
COPY .prod.env .env

# Copy only the built JAR from the first stage
COPY --from=build /app/target/*.jar app.jar

# Expose port 9090
EXPOSE 9090

# Run the application
CMD export $(cat .prod.env | xargs) && java -jar app.jar
