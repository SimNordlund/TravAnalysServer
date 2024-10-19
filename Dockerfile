# Step 1: Build the application using Gradle
FROM gradle:8.2.1-jdk19 AS build

# Set the working directory inside the container
WORKDIR /app

# Copy Gradle wrapper and configuration files
COPY gradlew settings.gradle build.gradle ./
COPY gradle ./gradle

# Copy the source code
COPY src ./src

# Grant execution permission to the Gradle wrapper
RUN chmod +x gradlew

# Build the application
RUN ./gradlew build --no-daemon

# Step 2: Create a smaller image for running the application
FROM eclipse-temurin:19-jre

# Copy the JAR file from the build stage
COPY --from=build /app/build/libs/*.jar /app.jar

# Expose the port on which the application will run
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "/app.jar"]
