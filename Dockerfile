FROM openjdk:11.0-jdk as builder

RUN mkdir -p /app/source

COPY . /app/source

WORKDIR /app/source

RUN ./mvnw clean package

FROM openjdk:11.0-jre

COPY --from=builder /app/source/target/*.jar /app/app.jar

EXPOSE 8888

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom", "-jar", "/app/app.jar"]