#
# Build stage
#
FROM maven:3.8.3-openjdk-17 AS build
MAINTAINER Arpit Gupta <gupta.arpit03@gmail.com>
COPY . .
RUN mvn clean package -Pprod -DskipTests

#
# Package stage
#
FROM openjdk:17.0.1-jdk-slim
COPY --from=build /target/bluetick-0.0.1-SNAPSHOT.jar bluetick.jar
# ENV PORT=8080
EXPOSE 8080
ENTRYPOINT ["java","-jar","bluetick.jar"]