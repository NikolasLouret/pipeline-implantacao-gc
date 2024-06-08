# Use the official Amazon Corretto image as the base image
FROM amazoncorretto:21

# Set the working directory inside the container
WORKDIR /app

# Copy the pom.xml and mvn files to the container
COPY api-clinica-medica/pom.xml .
COPY api-clinica-medica/.mvn .mvn
COPY api-clinica-medica/mvnw .
COPY api-clinica-medica/mvnw.cmd .

# Copy the src file to the container
COPY api-clinica-medica/src /app/src

# Install the dependencies and compile the project
RUN chmod +x mvnw
RUN ./mvnw clean package

# Export the port
EXPOSE 8080

# Define the command to run the application
CMD ["java", "-jar", "target/api-clinica-medica-0.0.1-SNAPSHOT.jar"]
