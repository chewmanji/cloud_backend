FROM openjdk:17-jdk-alpine AS base
WORKDIR /app

FROM base AS build
COPY . .
RUN chmod +x ./gradlew
RUN ./gradlew clean build

FROM base
COPY --from=build /app/build/libs/ChatApp-0.0.1-SNAPSHOT.jar /app/app.jar

EXPOSE 8081

CMD ["java","-jar","app.jar"]