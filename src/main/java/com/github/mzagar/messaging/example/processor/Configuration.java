package com.github.mzagar.messaging.example.processor;

import com.github.mzagar.messaging.example.common.message.Message;

import java.util.Properties;

/**
 * Created by mzagar on 11.9.2014.
 */
public class Configuration {

    private static final String DEFAULT_QUEUE_NAME = "messageQueue";
    private static final String DEFAULT_QUEUE_HOST = "localhost";
    private static final String DEFAULT_MESSAGE_PROCESSOR_TYPE = MessageProcessorType.TYPE_A.name();

    private final String queueuName;
    private final String queueHost;
    private final String messageProcessorType;

    public Configuration() {
        this(new Properties());
    }

    public Configuration(Properties properties) {
        queueuName = System.getProperty("queue.name", DEFAULT_QUEUE_NAME);
        queueHost = System.getProperty("queue.host", DEFAULT_QUEUE_HOST);
        messageProcessorType = System.getProperty("processory.type", DEFAULT_MESSAGE_PROCESSOR_TYPE);
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
}
