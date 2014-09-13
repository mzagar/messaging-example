package messaging.gateway.message;

/**
 * Thrown by {@link messaging.gateway.message.MessageValidator}.
 *
 * Created by mzagar on 11.9.2014.
 */
public class MessageValidationException extends Exception {
    public MessageValidationException(String message) {
        super(message);
    }
}
