package com.github.mzagar.messaging.example.common.queue;

import com.github.mzagar.messaging.example.common.message.Message;
import com.github.mzagar.messaging.example.processor.MessageProcessor;

/**
 * Created by mzagar on 11.9.2014.
 */
public interface MessageQueueProducer extends MessageQueue {
    void produce(Message message) throws MessageQueueException;
}
