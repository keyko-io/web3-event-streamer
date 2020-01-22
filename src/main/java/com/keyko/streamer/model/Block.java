package com.keyko.streamer.model;

public class Block {

    public String id;
    public Detail detail;

    public static class Detail {

        public String hash;
        public String nodeName;
        public Long timestamp;
    }
}
