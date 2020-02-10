FROM maven:3.6.3-jdk-11

WORKDIR /
COPY pom.xml /
COPY src /

RUN mvn package

COPY /target/keyko-event-streamer-0.0.1-allinone.jar /event-streamer.jar
CMD java -jar event-streamer.jar
