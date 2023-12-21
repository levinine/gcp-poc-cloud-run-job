FROM openjdk:17
MAINTAINER gcp.poc
COPY build/libs/weather-data-publishr-0.0.1-SNAPSHOT.jar weather-data-publishr-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/weather-data-publishr-0.0.1-SNAPSHOT.jar"]