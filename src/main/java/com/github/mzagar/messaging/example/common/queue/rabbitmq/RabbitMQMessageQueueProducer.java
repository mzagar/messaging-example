package com.github.mzagar.messaging.example.common.queue.rabbitmq;

import com.github.mzagar.messaging.example.common.message.Message;
import com.github.mzagar.messaging.example.common.queue.MessageQueueConsumer;
import com.github.mzagar.messaging.example.common.queue.MessageQueueException;
import com.github.mzagar.messaging.example.common.queue.MessageQueueProducer;
import com.github.mzagar.messaging.example.processor.MessageProcessor;

import java.io.IOException;

/**
 * Created by mzagar on 11.9.2014.
 */
public class RabbitMQMessageQueueProducer extends AbstractRabbitMQMessageQueue implements MessageQueueProducer {
    public RabbitMQMessageQueueProducer(String host, String queueName) {
        super(host, queueName);
    }

    @Override
    public void produce(Message message) throws MessageQueueException {
        try {
            channel.basicPublish("", queueName, null, serializer.toJson(message).getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
