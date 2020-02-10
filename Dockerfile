FROM openjdk:8-jre

COPY target/keyko-event-streamer-0.0.1-allinone.jar /event-streamer.jar
CMD java -jar event-streamer.jar
