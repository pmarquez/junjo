FROM eclipse-temurin:17-jre-focal

# copy WAR into image
COPY /junjo-0.7.0.jar /junjo.jar

EXPOSE 8100

ENV application.environment=localhost \
    spring.data.mongodb.database=junjo \
    spring.data.mongodb.uri=mongodb://host.docker.internal:27017 \
    logging.level.org.springframework.data=debug

# run application with this command line
CMD ["java", "-jar", "-Dspring.profiles.active=DEV", "junjo.jar"]