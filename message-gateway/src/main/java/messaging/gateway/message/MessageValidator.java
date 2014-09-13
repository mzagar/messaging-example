package messaging.gateway.message;

/**
 * Implements logic that validates message model.
 *
 * Created by mzagar on 11.9.2014.
 */
public class MessageValidator {
    /**
     * Validates message model.
     *
     * @param message to validate
     * @throws MessageValidationException if not valid
     */
    public void validate(Message message) throws MessageValidationException {
        if (message.getMessageId() <= 0) {
            throw new MessageValidationException("messageId must be > 0 but is " + message.getMessageId());
        }

        if (message.getTimestamp() <= 0) {
            throw new MessageValidationException("timestamp must be > 0 but is " + message.getTimestamp());
        }

        if (!message.getProtocolVersion().matches("[0-9]?[0-9].[0-9].[0-9]")) {
            throw new MessageValidationException("protocolVersion must be in format ##.#.# but is " + message.getProtocolVersion());
        }

        if (!Versions.isSupported(message.getProtocolVersion())) {
            throw new MessageValidationException("Unsupported protocol version: " + message.getProtocolVersion() + ". Supported protocol versions: " + Versions.getSupportedVersions());
        }
    }
}
