# Step 1: Build the application using Gradle
FROM gradle:8.2.1-jdk20 AS build

# Set the working directory inside the container
WORKDIR /app

# Copy the build.gradle, settings.gradle, and src folder to the container
COPY ./build.gradle ./
COPY ./settings.gradle ./
COPY ./src ./src

# Build the application
RUN gradle clean build //Changes: Use Gradle to build the application

# Step 2: Create a smaller image for running the application
FROM openjdk:20-jdk-slim

# Copy the JAR file from the build stage
COPY --from=build /app/build/libs/*.jar /app.jar

# Expose the port on which the application will run
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "/app.jar"]
