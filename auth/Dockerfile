FROM azul/zulu-openjdk:17
COPY build/libs/auth-1.0.jar auth.jar
VOLUME /tmp
ENTRYPOINT ["java", "-jar", "/auth.jar"]