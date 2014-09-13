package messaging.gateway.queue;


import messaging.gateway.queue.rabbitmq.RabbitMQMessageQueuePublisher;

/**
 * Implements logic to create instances of {@link messaging.gateway.queue.MessageQueuePublisher}.
 *
 * Created by mzagar on 11.9.2014.
 */
public class MessageQueuePublisherFactory {
    public MessageQueuePublisher createPublisher(String host, String queueName) {
        return new RabbitMQMessageQueuePublisher(host, queueName);
    }
}
