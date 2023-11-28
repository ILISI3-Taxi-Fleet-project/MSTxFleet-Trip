FROM openjdk:17-jdk-alpine
COPY target/MSTxFleet-Trip-0.0.1-SNAPSHOT.jar MSTxFleet-Trip.jar
ENTRYPOINT ["java","-Dspring.profiles.active=prod","-jar","/MSTxFleet-Trip.jar"]