package messaging.gateway.message;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.*;

public class MessageFactoryTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void test_message100() throws MessageValidationException {
        String message1="{\"messageId\":208, \"timestamp\":123456789, \"protocolVersion\":\"1.0.0\", \"messageData\":{\"mMX\":212234, \"mPermGen\":552232}}";
        Message m = new MessageFactory().createFromJson(message1);
        assertEquals(new Message(208, 123456789, "1.0.0", new MessageData(212234, 552232, null, null)), m);
    }

    @Test
    public void test_message101() throws MessageValidationException {
        String message1="{\"messageId\":208, \"timestamp\":123456789, \"protocolVersion\":\"1.0.1\", \"messageData\":{\"mMX\":212234, \"mPermGen\":552232, \"mOldGen\":444333}}";
        Message m = new MessageFactory().createFromJson(message1);
        assertEquals(new Message(208, 123456789, "1.0.1", new MessageData(212234, 552232, 444333, null)), m);
    }

    @Test
    public void test_message200() throws MessageValidationException {
        String message1="{\"messageId\":208, \"timestamp\":123456789, \"protocolVersion\":\"2.0.0\", \"payload\":{\"mMX\":212234, \"mPermGen\":552232, \"mOldGen\":444333, \"mYoungGen\":555444}}";
        Message m = new MessageFactory().createFromJson(message1);
        assertEquals(new Message(208, 123456789, "2.0.0", new MessageData(212234, 552232, 444333, 555444)), m);
    }

    @Test
    public void should_throw_if_expected_element_is_missing() {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("protocolVersion expected but is missing");

        String message1="{\"messageId\":208, \"timestamp\":123456789, \"messageData\":{\"mMX\":212234, \"mPermGen\":552232}}";
        new MessageFactory().createFromJson(message1);
    }
}