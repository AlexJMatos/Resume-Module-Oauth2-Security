FROM openjdk:8-jdk-alpine
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} resume-module-1.0.jar
ENTRYPOINT ["java","-jar","/resume-module-1.0.jar"]