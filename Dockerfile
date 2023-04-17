# Use the official OpenJDK image as the base image
FROM openjdk:8-jdk-alpine

# Set the working directory
WORKDIR /app

# Copy the Gradle build file to the container
COPY build.gradle.kts /app

# Copy the Gradle wrapper to the container
COPY gradlew /app
COPY gradle /app/gradle

# Copy the source code to the container
COPY src /app/src

# Build the application
RUN ./gradlew build

# Copy the built JAR file to a new stage
FROM openjdk:8-jre-alpine
COPY --from=0 /app/build/libs/*.jar /app/shop.jar

# Expose the application's port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "/app/shop.jar"]
