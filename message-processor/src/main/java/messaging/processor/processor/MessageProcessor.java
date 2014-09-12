package messaging.processor.processor;

import messaging.processor.message.Message;

/**
 * Created by mzagar on 11.9.2014.
 */
public interface MessageProcessor {
    void process(Message message);
}
