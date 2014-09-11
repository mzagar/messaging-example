package com.github.mzagar.messaging.example.common.queue;

/**
 * Created by mzagar on 11.9.2014.
 */
public class MessageQueueException extends Exception {
    public MessageQueueException(String message) {
        super(message);
    }

    public MessageQueueException(String message, Throwable cause) {
        super(message, cause);
    }
}
