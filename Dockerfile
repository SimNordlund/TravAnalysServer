# Step 1: Build the application using Gradle with JDK 20
FROM gradle:8.2.1-jdk20 AS build

# Set the working directory inside the container
WORKDIR /app

# Copy the Gradle wrapper and build files
COPY gradlew gradlew
COPY gradle gradle
COPY build.gradle.kts settings.gradle.kts ./

# Ensure gradlew is executable
RUN chmod +x gradlew

# Copy the rest of the source code
COPY ./src ./src

# Build the application
RUN ./gradlew clean build -x test --no-daemon

# Step 2: Create a smaller image for running the application with Corretto 20
FROM amazoncorretto:20-alpine

# Copy the JAR file from the build stage
COPY --from=build /app/build/libs/*.jar /app.jar

# Expose the port on which the application will run
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "/app.jar"]
