FROM azul/zulu-openjdk:17
COPY build/libs/search-1.0.jar search.jar
VOLUME /tmp
ENTRYPOINT ["java", "-jar", "/search.jar"]