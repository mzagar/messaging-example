package com.github.mzagar.messaging.example.processor;

/**
 * Created by mzagar on 11.9.2014.
 */
public class MessageProcessorFactory {

    public MessageProcessor create(MessageProcessorType type) {
        if (MessageProcessorType.TYPE_A.equals(type)) {
            return new PrintMessageProcessor();
        }

        throw new IllegalArgumentException("message processor type " + type + " not implemented");
    }
}
