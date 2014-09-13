package messaging.processor;

import messaging.processor.processor.MessageProcessorType;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.util.Properties;

/**
 * Holds app configuration values.
 *
 * Defaults: <br>
 * <li>queue.name=messageGatewayProcessor</li>
 * <li>queue.host=localhost</li>
 * <li>processor.type=TYPE_A ({@link messaging.processor.processor.PrintMessageProcessor})</li>
 *
 * @see messaging.processor.processor.MessageProcessorType
 *
 * Created by mzagar on 11.9.2014.
 */
public final class Configuration {
    protected static final String DEFAULT_QUEUE_NAME = "messageGatewayQueue";
    protected static final String DEFAULT_QUEUE_HOST = "localhost";
    protected static final String DEFAULT_MESSAGE_PROCESSOR_TYPE = MessageProcessorType.TYPE_A.name();

    private final String queueuName;
    private final String queueHost;
    private final String messageProcessorType;

    private Configuration(Properties properties) {
        queueuName = properties.getProperty("queue.name", DEFAULT_QUEUE_NAME);
        queueHost = properties.getProperty("queue.host", DEFAULT_QUEUE_HOST);
        messageProcessorType = properties.getProperty("processor.type", DEFAULT_MESSAGE_PROCESSOR_TYPE);
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

    public static Configuration createDefault() {
        return new Configuration(new Properties());
    }

    public static Configuration createfromFile(final File configFile) throws IOException {
        InputStream is = new FileInputStream(configFile);
        try {
            Properties properties = new Properties();
            properties.load(is);
            return new Configuration(properties);
        } finally {
            IOUtils.closeQuietly(is);
        }
    }
}
