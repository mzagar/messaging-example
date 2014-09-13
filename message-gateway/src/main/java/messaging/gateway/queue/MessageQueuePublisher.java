package messaging.gateway.queue;

import messaging.gateway.message.Message;

/**
 * Message publisher interface.
 *
 * Created by mzagar on 11.9.2014.
 */
public interface MessageQueuePublisher {
    void start();
    void stop();
    void publish(Message message) throws MessageQueuePublisherException;
}
