package com.github.mzagar.messaging.example.common.queue.rabbitmq;

import com.github.mzagar.messaging.example.common.message.Message;
import com.github.mzagar.messaging.example.processor.MessageProcessor;
import com.github.mzagar.messaging.example.common.queue.MessageQueue;
import com.github.mzagar.messaging.example.common.queue.MessageQueueException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

import java.io.IOException;

/**
 * Created by mzagar on 11.9.2014.
 */
public abstract class AbstractRabbitMQMessageQueue implements MessageQueue {

    protected Connection connection;
    protected Channel channel;
    private ConnectionFactory connectionFactory;

    protected final String queueName;
    protected final Gson serializer = new GsonBuilder().create();

    public AbstractRabbitMQMessageQueue(String host, String queueName) {
        this.queueName = queueName;
        this.connectionFactory = new ConnectionFactory();
        this.connectionFactory.setHost(host);
    }

    public void start() {
        try {
            connection = connectionFactory.newConnection();
            channel = connection.createChannel();
            channel.queueDeclare(queueName, true, false, false, null);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public void stop() {
        try {
            channel.close();
            connection.close();
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
