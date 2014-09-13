package messaging.gateway.message;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class MessageValidatorTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private MessageValidator validator = new MessageValidator();

    @Test
    public void should_not_throw_for_valid_message() throws MessageValidationException {
        validator.validate(new Message(1, 1, "1.0.0", null));
        validator.validate(new Message(1, 1, "1.0.1", null));
        validator.validate(new Message(1, 1, "2.0.0", null));
    }

    @Test
    public void should_throw_for_unsupported_version() throws MessageValidationException {
        expectedException.expect(MessageValidationException.class);
        validator.validate(new Message(1, 1, "a.b.c", null));
    }

    @Test
    public void should_throw_if_messageId_is_not_positive() throws MessageValidationException {
        expectedException.expect(MessageValidationException.class);
        validator.validate(new Message(-1, 1, "1.0.0", null));
    }

}