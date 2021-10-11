FROM maven:3.5.4-jdk-8-alpine AS MAVEN_BUILD
COPY pom.xml /build/
COPY src /build/src/
WORKDIR /build/
RUN mvn package -DskipTests
FROM openjdk:8-jre-alpine
WORKDIR /app
COPY --from=MAVEN_BUILD /build/target/hello-docker-0.0.1-SNAPSHOT.jar /app/
ENTRYPOINT ["java", "-jar", "hello-docker-0.0.1-SNAPSHOT.jar"]
#
#FROM openjdk:8-jre-alpine
#WORKDIR /app
#COPY target/hello-docker-0.0.1-SNAPSHOT.jar /app/
#ENTRYPOINT ["java", "-jar", "hello-docker-0.0.1-SNAPSHOT.jar"]