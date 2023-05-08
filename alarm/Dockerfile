FROM azul/zulu-openjdk:17
COPY build/libs/alarm-1.0.jar alarm.jar
VOLUME /tmp
ENTRYPOINT ["java", "-jar", "/alarm.jar"]