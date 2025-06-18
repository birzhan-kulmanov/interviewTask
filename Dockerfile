FROM openjdk:21-jdk-slim
VOLUME /tmp
ARG JAR_FILE=target/test-task.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]