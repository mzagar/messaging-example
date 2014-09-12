package messaging.processor.message;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Message message = (Message) o;

        if (messageId != message.messageId) return false;
        if (timestamp != message.timestamp) return false;
        if (messageData != null ? !messageData.equals(message.messageData) : message.messageData != null) return false;
        if (!protocolVersion.equals(message.protocolVersion)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = messageId;
        result = 31 * result + timestamp;
        result = 31 * result + protocolVersion.hashCode();
        result = 31 * result + (messageData != null ? messageData.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Message{" +
                "messageId=" + messageId +
                ", timestamp=" + timestamp +
                ", protocolVersion='" + protocolVersion + '\'' +
                ", messageData=" + messageData +
                '}';
    }
}
