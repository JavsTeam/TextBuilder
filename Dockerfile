FROM ubuntu:20.10
LABEL mainainer="tdoktora@inbox.ru"
ENV ADMIN="JAVSTEAM"
ENV DEBIAN_FRONTEND noninteractive
RUN apt-get update && apt-get upgrade -y && apt-get -y install locales && apt-get install -q -y openjdk-15-jdk && apt-get install openjdk-15-jre && apt-get -y install wget && apt-get -y install maven && apt-get clean;
ENV LANG C.UTF-8
ENV LC_ALL C.UTF-8
RUN java --version
COPY ./ ./
RUN mvn clean compile assembly:single
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/target/TextGenerator-1.0-SNAPSHOT-jar-with-dependencies.jar"]