FROM openjdk
WORKDIR usr/src
EXPOSE 8085
ADD ./target/NotificationService-0.0.1-SNAPSHOT.jar /usr/src/NotificationService-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","NotificationService-0.0.1-SNAPSHOT.jar"]