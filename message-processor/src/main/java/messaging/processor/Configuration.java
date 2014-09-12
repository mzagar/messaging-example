package messaging.processor;

import messaging.processor.processor.MessageProcessorType;

import java.util.Properties;

/**
 * Created by mzagar on 11.9.2014.
 */
public class Configuration {
    private static final String DEFAULT_QUEUE_NAME = "messageGatewayQueue";
    private static final String DEFAULT_QUEUE_HOST = "localhost";
    private static final String DEFAULT_MESSAGE_PROCESSOR_TYPE = MessageProcessorType.TYPE_A.name();

    private final String queueuName;
    private final String queueHost;
    private final String messageProcessorType;

    public Configuration(Properties properties) {
        queueuName = properties.getProperty("queue.name", DEFAULT_QUEUE_NAME);
        queueHost = properties.getProperty("queue.host", DEFAULT_QUEUE_HOST);
        messageProcessorType = properties.getProperty("processory.type", DEFAULT_MESSAGE_PROCESSOR_TYPE);
    }

    public String getQueueuName() {
        return queueuName;
    }

    public String getQueueHost() {
        return queueHost;
    }

    public MessageProcessorType getMessageProcessorType() {
        return MessageProcessorType.valueOf(messageProcessorType);
    }

    @Override
    public String toString() {
        return "Configuration{" +
                "queueuName='" + queueuName + '\'' +
                ", queueHost='" + queueHost + '\'' +
                ", messageProcessorType='" + messageProcessorType + '\'' +
                '}';
    }
}
