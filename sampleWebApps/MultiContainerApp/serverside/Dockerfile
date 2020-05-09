FROM openjdk:15-oracle 
FROM maven:alpine
WORKDIR /springApp
COPY . .
RUN mvn clean

## this would've build the app as a jar, and then run the jar -- the issue is that it did not include application properties
## using spring-boot:run is a workaround for this issue
#RUN mvn clean install
#ARG JAR_FILE=target/*.jar
#COPY ${JAR_FILE} compiled/app.jar
#ENTRYPOINT ["java","-jar","compiled/app.jar"]

ENTRYPOINT ["mvn", "spring-boot:run"]
