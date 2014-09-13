package messaging.processor.processor;


import messaging.processor.message.Message;
import messaging.processor.message.MessageData;

/**
 * Prints message to std out.
 *
 * Created by mzagar on 11.9.2014.
 */
public class PrintMessageProcessor implements MessageProcessor {
    @Override
    public void process(Message message) {
        print(message);
    }

    protected void print(Message message) {
        MessageData messageData = message.getMessageData();

        System.out.printf("Message: id=%s, timestamp=%s, protocolVersion=%s, messageData: mMx=%s, mPermGen=%s, mOldGen=%s, mYoungGen=%s\n",
                message.getMessageId(),
                message.getTimestamp(),
                message.getProtocolVersion(),
                messageData != null ? messageData.getMX() : "N/A",
                messageData != null ? messageData.getPermGen() : "N/A",
                messageData != null ? messageData.getOldGen() : "N/A",
                messageData != null ? messageData.getYoungGen() : "N/A"
        );
    }
}
