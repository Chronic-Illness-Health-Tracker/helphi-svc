FROM eclipse-temurin:17-jdk-alpine
EXPOSE 8080
EXPOSE 8443
VOLUME /tmp
ARG JAR_FILE
COPY ${JAR_FILE} /app.jar
ENTRYPOINT ["java","-jar","/app.jar"]