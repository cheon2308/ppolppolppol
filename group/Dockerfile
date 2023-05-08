FROM azul/zulu-openjdk:17
COPY build/libs/group-1.0.jar group.jar
VOLUME /tmp
ENTRYPOINT ["java", "-jar", "/group.jar"]