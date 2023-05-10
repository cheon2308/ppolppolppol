FROM azul/zulu-openjdk:17
COPY build/libs/online-server-1.0.jar online-server.jar
VOLUME /tmp
ENTRYPOINT ["java", "-jar", "/online-server.jar"]