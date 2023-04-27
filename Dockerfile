FROM adoptopenjdk:17-jdk-hotspot AS builder
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .
COPY src src
RUN chmod +x ./gradlew
RUN ./gradlew bootJAR

FROM adoptopenjdk:17-jdk-hotspot
COPY --from=builder build/libs/*.jar eureka.jar
EXPOSE 8761
ENTRYPOINT ["java", "-jar", "/eureka.jar"]