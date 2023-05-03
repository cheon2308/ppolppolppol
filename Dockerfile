FROM azul/zulu-openjdk:17
COPY build/libs/personal-1.0.jar personal.jar
VOLUME /tmp
ENTRYPOINT ["java", "-jar", "/personal.jar"]