package messaging.processor;


import messaging.processor.processor.MessageProcessor;
import messaging.processor.processor.MessageProcessorFactory;
import messaging.processor.queue.MessageQueueConsumer;
import messaging.processor.queue.MessageQueueConsumerException;
import messaging.processor.queue.MessageQueueConsumerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * Message processor main entry point.<p>
 *
 * Starts message consumer which consumes messages and sends them to MessageProcessor for processing.<br>
 * Accepts one (optional) argument: configuration file name where queue name, queue host and message processor
 * type can be specified. <br>
 * If no configuration file is specified defaults are used.br
 * See {@link messaging.processor.Configuration} for configuration details.
 * <p>
 *
 * Run like this:<p>
 * <i>java -jar target/messaging-processor-1.0-SNAPSHOT.jar etc/config.properties</i>
 *
 *
 *
 *
 * Created by mzagar on 11.9.2014.
 */
public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws Exception {
        // only 1 optional parameter is allowed: configuration properties file
        if (args.length > 1) {
            throw new IllegalArgumentException("Expecting only one (optional) argument: configuration file");
        }

        final Configuration configuration = args.length == 0 ? Configuration.createDefault() : Configuration.createfromFile(new File(args[0]));

        logger.info("Using configuration: {}", configuration);

        final MessageQueueConsumer consumer = new MessageQueueConsumerFactory().createConsumer(configuration.getQueueHost(), configuration.getQueueuName());
        final MessageProcessor messageProcessor = new MessageProcessorFactory().create(configuration.getMessageProcessorType());

        logger.info("Starting message consumer...");
        consumer.start();

        logger.info("Waiting to process messages...");
        while (true) {
            try {
                consumer.consume(messageProcessor);
            } catch(MessageQueueConsumerException e) {
                logger.error("Error processing message: " + e.getMessage(), e);
            }
        }
    }
}