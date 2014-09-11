package com.github.mzagar.messaging.example.common.message;

/**
 * Created by mzagar on 11.9.2014.
 */
public class Message {
    private int messageId;
    private int timestamp;
    private String protocolVersion;
    private MessageData messageData;

    public Message(int messageId, int timestamp, String protocolVersion, MessageData messageData) {
        this.messageId = messageId;
        this.timestamp = timestamp;
        this.protocolVersion = protocolVersion;
        this.messageData = messageData;
    }

    public int getMessageId() {
        return messageId;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public String getProtocolVersion() {
        return protocolVersion;
    }

    public MessageData getMessageData() {
        return messageData;
    }
}
