FROM openjdk
WORKDIR usr/src
EXPOSE 8082
ADD ./target/ArchiveTasks-0.0.1-SNAPSHOT.jar /usr/src/ArchiveTasks-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","ArchiveTasks-0.0.1-SNAPSHOT.jar"]