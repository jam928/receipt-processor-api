# TODO gradle build

# Use a base image with Java
FROM eclipse-temurin:17-jdk

# Set working directory
WORKDIR /app

# Copy the built jar file (Assuming it's named app.jar)
COPY build/libs/sandbox-blocking-service-0.0.1-SNAPSHOT.jar app.jar

# Expose the application port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]