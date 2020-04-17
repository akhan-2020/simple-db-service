package com.akhan.mongo.demo;

public class Message {

    private String to;
    private String body;

    public Message() {
    }

    public Message(String to, String body) {
        this.to = to;
        this.body = body;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return String.format("SMSMessage{to=%s, body=%s}", getTo(), getBody());
    }

}