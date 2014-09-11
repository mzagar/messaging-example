package com.github.mzagar.messaging.example.processor;

import com.github.mzagar.messaging.example.common.queue.MessageQueue;
import com.github.mzagar.messaging.example.common.queue.MessageQueueConsumer;
import com.github.mzagar.messaging.example.common.queue.MessageQueueException;
import com.github.mzagar.messaging.example.common.queue.MessageQueueFactory;

import java.io.*;
import java.util.Properties;

/**
 * Created by mzagar on 11.9.2014.
 */
public class Main {
    public static void main(String[] args) throws MessageQueueException {
        System.out.println("Starting message processor...");

        System.out.println("Creating configuration...");
        Configuration configuration = createConfiguration(args);

        final MessageQueueConsumer consumer = new MessageQueueFactory().createConsumer(configuration.getQueueHost(), configuration.getQueueuName());
        final MessageProcessor messageProcessor = new MessageProcessorFactory().create(configuration.getMessageProcessorType());

        makeSureMessageQueueIsStoppedOnShutdown(consumer);

        System.out.println("Starting message queue...");
        consumer.start();

        System.out.println("Waiting to process messages...");
        while (true) {
            consumer.consume(messageProcessor);
        }
    }

    private static Configuration createConfiguration(String[] args) {
        if (args.length == 0) {
            return new Configuration();
        }

        if (args.length == 1) {
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

        throw new IllegalArgumentException("unsupported number of arguments: expecting only optional configuration file name");
    }

    private static void makeSureMessageQueueIsStoppedOnShutdown(final MessageQueue messageQueue) {
        final Thread mainThread = Thread.currentThread();
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                System.out.println("Stopping message queue...");
                messageQueue.stop();
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