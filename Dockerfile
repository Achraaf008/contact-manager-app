FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/contactmanager-0.0.1-SNAPSHOT.jar /app/contactmanager.jar

EXPOSE 8060

CMD ["java", "-jar", "contactmanager.jar"]
