FROM openjdk
WORKDIR use/lib
ENV MONGO_DATABASE=todoservice
ENV MONGO-URL=mongodb://localhost:27017/todoservice

ADD .target/ToDoService-0.0.1-SNAPSHOT.jar /usr/lib/ToDoService-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java","-jar","ToDoService-0.0.1-SNAPSHOT.jar"]