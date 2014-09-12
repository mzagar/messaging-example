package messaging.processor.processor;


import messaging.processor.message.Message;
import messaging.processor.message.MessageData;

/**
 * Created by mzagar on 11.9.2014.
 */
public class PrintMessageProcessor implements MessageProcessor {
    @Override
    public void process(Message message) {
        print(message);
    }

    protected void print(Message message) {
        MessageData messageData = message.getMessageData();

        System.out.printf("Message: id=%s, timestamp=%s, protocolVersion=%s, body: mMx=%s, mPermGen=%s, mOldGen=%s, mYoungGen=%s\n",
                message.getMessageId(),
                message.getTimestamp(),
                message.getProtocolVersion(),
                messageData != null ? messageData.getmMX() : null,
                messageData != null ? messageData.getmPermGen() : null,
                messageData != null ? messageData.getmOldGen() : null,
                messageData != null ? messageData.getmYoungGen() : null
        );
    }
}
