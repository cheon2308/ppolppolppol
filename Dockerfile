FROM openjdk:17-jdk AS builder
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .
COPY src src
RUN chmod +x ./gradlew
RUN ./gradlew bootJAR

FROM openjdk:17-jdk
COPY --from=builder build/libs/*.jar eureka.jar
EXPOSE 8761
ENTRYPOINT ["java", "-jar", "/eureka.jar"]