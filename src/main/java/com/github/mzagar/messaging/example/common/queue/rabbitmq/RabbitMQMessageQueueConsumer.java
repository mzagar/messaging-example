package com.github.mzagar.messaging.example.common.queue.rabbitmq;

import com.github.mzagar.messaging.example.common.message.Message;
import com.github.mzagar.messaging.example.common.queue.MessageQueueConsumer;
import com.github.mzagar.messaging.example.common.queue.MessageQueueException;
import com.github.mzagar.messaging.example.processor.MessageProcessor;
import com.rabbitmq.client.QueueingConsumer;

import java.io.IOException;

/**
 * Created by mzagar on 11.9.2014.
 */
public class RabbitMQMessageQueueConsumer extends AbstractRabbitMQMessageQueue implements MessageQueueConsumer {
    private QueueingConsumer consumer;

    public RabbitMQMessageQueueConsumer(String host, String queueName) {
        super(host, queueName);
    }

    @Override
    public void start() {
        super.start();

        consumer = new QueueingConsumer(channel);
        try {
            channel.basicConsume(queueName, false, consumer); // false=manual ack after message is processed
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @Override
    public void consume(MessageProcessor messageProcessor) throws MessageQueueException {
        try {
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();

            Message message = createMessageFrom(delivery);

            messageProcessor.process(message);

            channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
        } catch(Exception e) {
            throw new MessageQueueException(e.getMessage(), e);
        }
    }

    private Message createMessageFrom(QueueingConsumer.Delivery delivery) {
        return serializer.fromJson(new String(delivery.getBody()), Message.class);
    }


}
