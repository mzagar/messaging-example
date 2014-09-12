package messaging.processor.queue;

/**
 * Created by mzagar on 11.9.2014.
 */
public class MessageQueueConsumerException extends Exception {
    public MessageQueueConsumerException(String message) {
        super(message);
    }

    public MessageQueueConsumerException(String message, Throwable cause) {
        super(message, cause);
    }
}
