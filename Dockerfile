FROM maven:3.8.1-openjdk-16-slim
LABEL mainainer="tdoktora@inbox.ru"
COPY ./ ./
RUN mvn clean package
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/target/Application.jar"]