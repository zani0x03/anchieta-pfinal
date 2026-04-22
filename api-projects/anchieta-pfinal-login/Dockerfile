# Use an official OpenJDK runtime as a parent image
FROM eclipse-temurin:17-jdk AS builder

# Set the working directory in the container
WORKDIR /app

# Copy the Maven wrapper and pom.xml
COPY .mvn/ .mvn
COPY mvnw pom.xml ./

# Download dependencies. This is separate from copying the code to leverage Docker cache.
RUN ./mvnw dependency:go-offline

# Copy the source code
COPY src ./src

# Package the application
RUN ./mvnw package -DskipTests

# Use a slim base image for the final stage
FROM eclipse-temurin:17-jdk AS final_stage
# Explicitly naming the final stage

# Set the working directory in the container
WORKDIR /app

# Copy the built JAR file from the builder stage
COPY --from=builder /app/target/*.jar app.jar

# Expose the application port
EXPOSE 8085

# Run the application
# The application will read configurations from environment variables
ENTRYPOINT ["java", "-jar", "app.jar"]
