package messaging.processor.queue;

import messaging.processor.message.Message;
import messaging.processor.processor.MessageProcessor;

/**
 * Implements basic logic and exception handling for getting message from queue and forwarding it to processor.<p>
 *
 * Concrete implementations must implement
 * {@link messaging.processor.queue.AbstractMessageConsumer#consume(messaging.processor.processor.MessageProcessor)}. <p>
 *
 * Created by mzagar on 13.9.2014.
 */
public abstract class AbstractMessageConsumer implements MessageQueueConsumer {

    @Override
    public final void consume(MessageProcessor messageProcessor) throws MessageQueueConsumerException {
        try {
            Message message = getMessageFromQueue();
            messageProcessor.process(message);
        } catch(MessageQueueConsumerException e) {
            throw e;
        } catch(Exception e) {
            throw new MessageQueueConsumerException(e.getMessage(), e);
        }
    }

    protected abstract Message getMessageFromQueue() throws InterruptedException, MessageQueueConsumerException;
}
