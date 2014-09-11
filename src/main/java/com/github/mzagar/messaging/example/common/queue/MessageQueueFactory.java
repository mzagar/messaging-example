package com.github.mzagar.messaging.example.common.queue;

import com.github.mzagar.messaging.example.common.queue.rabbitmq.AbstractRabbitMQMessageQueue;
import com.github.mzagar.messaging.example.common.queue.rabbitmq.RabbitMQMessageQueueConsumer;
import com.github.mzagar.messaging.example.common.queue.rabbitmq.RabbitMQMessageQueueProducer;

/**
 * Created by mzagar on 11.9.2014.
 */
public class MessageQueueFactory {
    public MessageQueueConsumer createConsumer(String host, String queueName) {
        return new RabbitMQMessageQueueConsumer(host, queueName);
    }

    public MessageQueueProducer createProducer(String host, String queueName) {
        return new RabbitMQMessageQueueProducer(host, queueName);
    }
}
