FROM openjdk:17
EXPOSE 8080

ARG JAR_FILE=target/*.jar 
COPY ./target/website-0.0.1-SNAPSHOT.jar website-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "/website-0.0.1-SNAPSHOT.jar"]
