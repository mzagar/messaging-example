package messaging.processor.processor;


import messaging.processor.message.Message;

/**
 * Prints message to std out - twice.
 *
 * Created by mzagar on 11.9.2014.
 */
public class PrintTwiceMessageProcessor extends PrintMessageProcessor {
    @Override
    public void process(Message message) {
        print(message);
        print(message);
    }
}
