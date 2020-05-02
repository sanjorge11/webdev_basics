FROM openjdk:15-oracle 
FROM maven:alpine
WORKDIR /springApp
COPY ./serverside/ .
RUN mvn clean install
ARG JAR_FILE=./serverside/target/*.jar
COPY ${JAR_FILE} compiled/app.jar
ENTRYPOINT ["java","-jar","compiled/app.jar"]

