FROM openjdk:11
ARG JAR_FILE=target/smartschool-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} app.jar
ADD avatar avatar
ENTRYPOINT ["java","-jar","app.jar"]
