package com.keyko.streamer.model;

public class Event {

    public String id;
    public Detail detail;

    public static class Detail {

        public String name;
        public String blockHash;
        public String status;

    }
}
