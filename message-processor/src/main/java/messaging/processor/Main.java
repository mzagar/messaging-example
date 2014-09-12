package messaging.processor;


import messaging.processor.processor.MessageProcessor;
import messaging.processor.processor.MessageProcessorFactory;
import messaging.processor.queue.MessageQueueConsumer;
import messaging.processor.queue.MessageQueueConsumerFactory;

import java.io.*;
import java.util.Properties;

/**
 * Created by mzagar on 11.9.2014.
 */
public class Main {
    public static void main(String[] args) throws Exception {
        final Configuration configuration = createConfiguration(args);
        System.out.println("Using: " + configuration);

        final MessageQueueConsumer consumer = new MessageQueueConsumerFactory().createConsumer(configuration.getQueueHost(), configuration.getQueueuName());
        final MessageProcessor messageProcessor = new MessageProcessorFactory().create(configuration.getMessageProcessorType());

//        makeSureMessageQueueConsumerIsStoppedOnShutdown(consumer);

        System.out.println("Starting message consumer...");
        consumer.start();

        System.out.println("Waiting to process messages...");
        while (true) {
            consumer.consume(messageProcessor);
        }
    }

    private static Configuration createConfiguration(String[] args) {
        // only 1 parameter is allowed: configuration properties file
        if (args.length > 1) {
            throw new IllegalArgumentException("unsupported number of arguments, expecting only one optional argument: configuration properties file name");
        }

        // create default configuration
        if (args.length == 0) {
            return new Configuration(new Properties());
        }

        // create Configuration based on properties specified in provided properties file
        InputStream is = null;
        try {
            File configFile = new File(args[1]);
            is = new FileInputStream(configFile);
            Properties properties = new Properties();
            properties.load(is);
            return new Configuration(properties);
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("error loading config file: " + e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException("error loading config file: " + e.getMessage());
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    // ignored
                }
            }
        }
    }

    private static void makeSureMessageQueueConsumerIsStoppedOnShutdown(final MessageQueueConsumer messageQueueConsumer) {
        final Thread mainThread = Thread.currentThread();
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                System.out.println("Stopping message queue...");
                messageQueueConsumer.stop();
                try {
                    mainThread.interrupt();
                    mainThread.join();
                    System.out.println("Terminated.");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e.getMessage(), e);
                }
            }
        });
    }
}