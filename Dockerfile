FROM openjdk:11.0-jdk as builder

RUN mkdir -p /app/source

COPY . /app/source

WORKDIR /app/source

RUN ./gradlew clean build

FROM openjdk:11.0-jre

COPY --from=builder /app/source/build/libs/*SNAPSHOT.jar /app/app.jar

EXPOSE 8888

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom", "-jar", "/app/app.jar"]