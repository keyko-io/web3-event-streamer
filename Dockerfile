FROM openjdk:8-jre
ADD target/keyko-event-streamer-0.0.1-allinone.jar keyko-event-streamer.jar

CMD java -jar keyko-event-streamer.jar
