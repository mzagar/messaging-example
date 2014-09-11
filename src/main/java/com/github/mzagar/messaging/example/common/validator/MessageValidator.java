package com.github.mzagar.messaging.example.common.validator;

import com.github.mzagar.messaging.example.common.message.Message;

/**
 * Created by mzagar on 11.9.2014.
 */
public class MessageValidator {
    public void validate(Message message) throws MessageValidationException {
        if (message.getMessageId() <= 0) {
            throw new MessageValidationException("messageId must be >= 0 but is " + message.getMessageId());
        }

        if (message.getTimestamp() <= 0) {
            throw new MessageValidationException("timestamp must be >= 0 but is " + message.getTimestamp());
        }

        if (!message.getProtocolVersion().matches("[0-9]?[0-9].[0-9].[0-9]")) {
            throw new MessageValidationException("protocolVersion must be in format ##.#.# but is " + message.getProtocolVersion());
        }
    }
}
