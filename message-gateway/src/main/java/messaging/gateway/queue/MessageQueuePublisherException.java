package messaging.gateway.queue;

/**
 * Thrown by {@link messaging.gateway.queue.MessageQueuePublisher#publish(messaging.gateway.message.Message)}.
 * Created by mzagar on 11.9.2014.
 */
public class MessageQueuePublisherException extends Exception {
    public MessageQueuePublisherException(String message) {
        super(message);
    }

    public MessageQueuePublisherException(String message, Throwable cause) {
        super(message, cause);
    }
}
