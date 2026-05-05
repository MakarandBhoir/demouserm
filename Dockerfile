FROM eclipse-temurin:21-jre

WORKDIR /app

COPY target/user-management.jar user-management.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "user-management.jar"]