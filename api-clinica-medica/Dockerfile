FROM amazoncorretto:21

RUN mkdir app
WORKDIR /app

COPY /target/api-clinica-medica-0.0.1-SNAPSHOT.jar /app/api-clinica-medica.jar

EXPOSE 8080
CMD ["java", "-jar", "api-clinica-medica.jar"]
