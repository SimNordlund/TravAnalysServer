# Step 1: Build the application using Gradle
FROM gradle:7.5.1-jdk17 AS build

# Set the working directory inside the container
WORKDIR /app

# Copy only the Gradle build files first (to cache dependencies)
COPY build.gradle.kts settings.gradle.kts ./

# Download dependencies to cache them
RUN gradle dependencies --no-daemon

# Copy the rest of the source code
COPY ./src ./src

# Build the application
RUN gradle clean build -x test --no-daemon

# Step 2: Create a smaller image for running the application
FROM openjdk:20-jdk-slim

# Copy the JAR file from the build stage
COPY --from=build /app/build/libs/*.jar /app.jar

# Expose the port on which the application will run
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "/app.jar"]
