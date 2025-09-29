package com.tuempresa.mensajeria.model;

public class Message {
    private long id;
    private long senderId;
    private long receiverId;
    private String body;
    private long timestamp;

    public Message() {}

    public Message(long id, long senderId, long receiverId, String body, long timestamp) {
        this.id = id; this.senderId = senderId; this.receiverId = receiverId; this.body = body; this.timestamp = timestamp;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    public long getSenderId() { return senderId; }
    public void setSenderId(long senderId) { this.senderId = senderId; }
    public long getReceiverId() { return receiverId; }
    public void setReceiverId(long receiverId) { this.receiverId = receiverId; }
    public String getBody() { return body; }
    public void setBody(String body) { this.body = body; }
    public long getTimestamp() { return timestamp; }
    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }
}
