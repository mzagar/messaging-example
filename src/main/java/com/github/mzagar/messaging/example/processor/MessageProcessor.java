package com.github.mzagar.messaging.example.processor;

import com.github.mzagar.messaging.example.common.message.Message;

/**
 * Created by mzagar on 11.9.2014.
 */
public interface MessageProcessor {
    void process(Message message);
}
