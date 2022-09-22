FROM gradle:7.5-jdk17-alpine AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle clean build --no-daemon

FROM openjdk:17.0.2-oracle
MAINTAINER Jonatas Tonholo

COPY --from=build /home/gradle/src/build/libs/*.jar /app.jar

EXPOSE 8080
ENV BLOCKHOUND_ENABLED false
ENV BLOCKHOUND_JUST_PRINT_STACKTRACE false
ENTRYPOINT ["java", "--enable-preview", "-XX:+ShowCodeDetailsInExceptionMessages", "-XX:+UseZGC", "-Dspring.profiles.active=default", "-jar", "app.jar"]