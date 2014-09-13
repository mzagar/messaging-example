package messaging.gateway.queue.rabbitmq;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import messaging.gateway.message.Message;
import messaging.gateway.queue.MessageQueuePublisher;
import messaging.gateway.queue.MessageQueuePublisherException;

import java.io.IOException;

/**
 * RabbitMQ message queue publisher implementation.
 *
 * Created by mzagar on 11.9.2014.
 */
public class RabbitMQMessageQueuePublisher implements MessageQueuePublisher {
    protected Connection connection;
    protected Channel channel;
    private ConnectionFactory connectionFactory;

    protected final String queueName;
    protected final Gson serializer = new GsonBuilder().create();

    public RabbitMQMessageQueuePublisher(String host, String queueName) {
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

    @Override
    public void publish(Message message) throws MessageQueuePublisherException {
        try {
            channel.basicPublish("", queueName, null, serializer.toJson(message).getBytes());
        } catch (IOException e) {
            throw new MessageQueuePublisherException(e.getMessage(), e);
        }
    }
}
