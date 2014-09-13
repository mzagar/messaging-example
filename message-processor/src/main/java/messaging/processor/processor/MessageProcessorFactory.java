package messaging.processor.processor;

/**
 * Creates message processor instances of specified type.
 *
 * Created by mzagar on 11.9.2014.
 */
public class MessageProcessorFactory {

    public MessageProcessor create(MessageProcessorType type) {
        if (MessageProcessorType.TYPE_A.equals(type)) {
            return new PrintMessageProcessor();
        }

        if (MessageProcessorType.TYPE_B.equals(type)) {
            return new PrintTwiceMessageProcessor();
        }

        throw new IllegalArgumentException("Unknown message processor type specified: " + type);
    }
}
