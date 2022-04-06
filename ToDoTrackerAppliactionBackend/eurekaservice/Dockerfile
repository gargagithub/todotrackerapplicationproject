FROM openjdk
WORKDIR usr/src
EXPOSE 8761
ADD ./target/eurekaservice-0.0.1-SNAPSHOT.jar /usr/src/eurekaservice-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","eurekaservice-0.0.1-SNAPSHOT.jar"]