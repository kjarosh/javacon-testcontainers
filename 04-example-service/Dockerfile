FROM alpine:3.13
RUN apk add openjdk11

ARG JAR_FILE="target/04-example-service-*-exec.jar"
COPY ${JAR_FILE} app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar" ]
