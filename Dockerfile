FROM openjdk:11.0.2-jre-stretch

WORKDIR /usr/app/weather-api-v2

COPY src/ ./src/
COPY gradle/ ./gradle/
COPY build.gradle.kts .
COPY gradle.properties .
COPY settings.gradle .
COPY gradlew .

RUN sh ./gradlew build

ENV temperatureAPIEndpoint "http://temperature:8000"
ENV windspeedAPIEndpoint "http://windspeed:8080"
CMD ["/bin/sh", "gradlew", "run"]