package com.github.mzagar.messaging.example.processor;

import com.github.mzagar.messaging.example.common.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by mzagar on 11.9.2014.
 */
public class PrintMessageProcessor implements MessageProcessor {

    private final Logger logger = LoggerFactory.getLogger(PrintMessageProcessor.class);

    private AtomicInteger count = new AtomicInteger(0);

    @Override
    public void process(Message message) {
        count.incrementAndGet();
        log(message);
    }

    private void log(Message message) {
        logger.info("{} - message: id={}, timestamp={}, protocolVersion={}, body: mMx={}, mPermGen={}",
                count.get(),
                message.getMessageId(),
                message.getTimestamp(),
                message.getProtocolVersion(),
                message.getMessageData().getmMX(),
                message.getMessageData().getmPermGen()
        );
    }
}
