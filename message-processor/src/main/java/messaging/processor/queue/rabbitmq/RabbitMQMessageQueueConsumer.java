package messaging.processor.queue.rabbitmq;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;
import messaging.processor.message.Message;
import messaging.processor.queue.AbstractMessageConsumer;
import messaging.processor.queue.MessageQueueConsumerException;

import java.io.IOException;

/**
 * RabbitMQ message consumer implementation.
 *
 * Created by mzagar on 11.9.2014.
 */
public class RabbitMQMessageQueueConsumer extends AbstractMessageConsumer {

    protected Connection connection;
    protected Channel channel;
    private ConnectionFactory connectionFactory;
    private QueueingConsumer consumer;

    protected final String queueName;
    protected final Gson serializer = new GsonBuilder().create();

    public RabbitMQMessageQueueConsumer(String host, String queueName) {
        this.queueName = queueName;
        this.connectionFactory = new ConnectionFactory();
        this.connectionFactory.setHost(host);
    }

    public void start() {
        try {
            connection = connectionFactory.newConnection();
            channel = connection.createChannel();
            channel.queueDeclare(queueName, true, false, false, null);
            consumer = new QueueingConsumer(channel);
            channel.basicConsume(queueName, true, consumer); // false=manual ack after message is processed
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

    @Override
    protected Message getMessageFromQueue() throws MessageQueueConsumerException {
        try {
            return createMessageFrom(consumer.nextDelivery());
        } catch (InterruptedException e) {
            throw new MessageQueueConsumerException(e.getMessage(), e);
        }
    }

    private Message createMessageFrom(QueueingConsumer.Delivery delivery) {
        return serializer.fromJson(new String(delivery.getBody()), Message.class);
    }

}
