package messaging.gateway.message;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by mzagar on 11.9.2014.
 */
public class MessageValidator {

    private Set<String> supportedVersions = new HashSet<String>();

    public MessageValidator() {
        supportedVersions.add("1.0.0");
        supportedVersions.add("1.0.1");
        supportedVersions.add("2.0.0");
    }

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

        if (!supportedVersions.contains(message.getProtocolVersion())) {
            throw new MessageValidationException("unsupported protocol version: " + message.getProtocolVersion() + ". Supported protocol versions: " + supportedVersions);
        }
    }
}
