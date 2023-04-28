FROM azul/zulu-openjdk:17
COPY build/libs/message-1.0.jar message.jar
VOLUME /tmp
ENTRYPOINT ["java", "-jar", "/message.jar"]