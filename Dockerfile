FROM azul/zulu-openjdk:17
COPY build/libs/article-1.0.jar article.jar
VOLUME /tmp
ENTRYPOINT ["java", "-jar", "/article.jar"]