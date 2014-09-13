package messaging.processor.queue;


import messaging.processor.queue.rabbitmq.RabbitMQMessageQueueConsumer;

/**
 * Implements logic for creating message queue consumer instances.
 *
 * Created by mzagar on 11.9.2014.
 */
public class MessageQueueConsumerFactory {
    public MessageQueueConsumer createConsumer(String host, String queueName) {
        return new RabbitMQMessageQueueConsumer(host, queueName);
    }
}
