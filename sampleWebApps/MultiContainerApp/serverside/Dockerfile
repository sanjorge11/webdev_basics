FROM openjdk:15-oracle 
FROM maven:alpine
WORKDIR /springApp
##COPY ./serverside/ .
COPY . .
RUN mvn clean
RUN mvn clean install
##ARG JAR_FILE=./serverside/target/*.jar
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} compiled/app.jar
#ENTRYPOINT ["java","-jar","compiled/app.jar"]
ENTRYPOINT ["mvn", "spring-boot:run"]
#RUN mvn spring-boot:run
