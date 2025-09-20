# Use an official OpenJDK image as a base image
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the built jar file into the container
# For Maven users
COPY target/inventory-0.0.1-SNAPSHOT.jar app.jar

# Expose the application port (change if necessary)
EXPOSE 8086

# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]