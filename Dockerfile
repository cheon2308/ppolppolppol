FROM azul/zulu-openjdk:17
COPY build/libs/*.jar eureka.jar
EXPOSE 8761
VOLUME /tmp
ENTRYPOINT ["java", "-jar", "/eureka.jar"]