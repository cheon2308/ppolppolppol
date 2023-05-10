FROM azul/zulu-openjdk:17
VOLUME /tmp
COPY build/libs/api-gateway-1.0.jar app.jar
ENTRYPOINT ["java", "-jar","app.jar"]