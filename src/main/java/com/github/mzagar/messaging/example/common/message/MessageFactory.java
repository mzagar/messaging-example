package com.github.mzagar.messaging.example.common.message;

import com.github.mzagar.messaging.example.common.validator.MessageValidationException;
import com.github.mzagar.messaging.example.common.validator.MessageValidator;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by mzagar on 11.9.2014.
 */
public class MessageFactory {

    private final Gson gson = new GsonBuilder().create();
    private final MessageValidator messageValidator = new MessageValidator();

    public Message createFromJson(String json) throws MessageValidationException {
        Message message = gson.fromJson(json, Message.class);
        messageValidator.validate(message);
        return message;
    }
}
