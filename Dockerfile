FROM gradle:8.4.0-jdk21-alpine AS build
WORKDIR /app
COPY . .
RUN gradle clean assemble -x test

FROM amazoncorretto:21
WORKDIR /app
COPY --from=build /app/build/libs/eldorg-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
