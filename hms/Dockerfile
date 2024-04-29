FROM eclipse-temurin:17-jdk AS build

WORKDIR /app

COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .

COPY src src

RUN ./gradlew build --no-daemon -x test

FROM eclipse-temurin:17-jre

WORKDIR /app

COPY --from=build /app/build/libs/hms-0.0.1-SNAPSHOT.jar .

EXPOSE 8080

CMD ["java", "-jar", "hms-0.0.1-SNAPSHOT.jar"]
