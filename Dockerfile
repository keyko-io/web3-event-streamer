FROM maven:3.6.3-jdk-11

WORKDIR /
COPY pom.xml /
COPY src /

RUN mvn package

CMD java -jar /target/keyko-event-streamer-0.0.1-allinone.jar
