# OpenJDK 17
FROM eclipse-temurin:17-jdk-alpine

# Set the working directory inside the container
WORKDIR /app

# Copy the built jar from the target directory to the container
COPY target/oms-0.0.1-SNAPSHOT.jar order-service.jar

# port on which the app runs
EXPOSE 8080

# Command to run the application
ENTRYPOINT ["java", "-jar", "order-service.jar"]