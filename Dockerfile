FROM gradle:8.14.3 as builder

COPY --chown=gradle:gradle . /home/gradle/src

WORKDIR /home/gradle/src

RUN gradle clean build -Pvaadin.productionMode=true

FROM amazoncorretto:21-alpine-jdk

WORKDIR /app

COPY --from=builder /home/gradle/src/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]