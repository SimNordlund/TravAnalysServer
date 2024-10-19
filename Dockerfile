# Step 1: Build the application using Gradle with JDK 21
FROM gradle:8.3-jdk21 AS build

# Set the working directory inside the container
WORKDIR /app

# Copy the Gradle wrapper and configuration files
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .

# Copy the source code
COPY src src

# Make the Gradle wrapper executabl
RUN chmod +x gradlew

# Build the application using the Gradle wrapper
RUN ./gradlew clean build

# Step 2: Create a smaller image for running the application
FROM amazoncorretto:21

# Copy the JAR file from the build stage
COPY --from=build /app/build/libs/*.jar /app.jar

# Expose the port on which the application will run
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "/app.jar"]
