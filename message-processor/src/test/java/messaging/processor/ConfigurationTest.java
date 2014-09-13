package messaging.processor;

import messaging.processor.processor.MessageProcessorType;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import static org.junit.Assert.assertEquals;

public class ConfigurationTest {

    @Test
    public void test_default_configuration() {
        Configuration c = Configuration.createDefault();

        assertEquals(Configuration.DEFAULT_QUEUE_NAME, c.getQueueuName());
        assertEquals(Configuration.DEFAULT_QUEUE_HOST, c.getQueueHost());
        assertEquals(MessageProcessorType.TYPE_A, c.getMessageProcessorType());
    }

    @Test
    public void test_load_from_fully_specified_file() throws Exception {
        Configuration c = Configuration.createfromFile(
                new File(ConfigurationTest.class.getResource("/config-full.properties").toURI())
        );

        assertEquals("queueName", c.getQueueuName());
        assertEquals("hostName", c.getQueueHost());
        assertEquals(MessageProcessorType.TYPE_B, c.getMessageProcessorType());
    }

    @Test
    public void test_load_from_partially_specified_file() throws Exception  {
        Configuration c = Configuration.createfromFile(
                new File(ConfigurationTest.class.getResource("/config-partial.properties").toURI())
        );

        assertEquals(Configuration.DEFAULT_QUEUE_NAME, c.getQueueuName());
        assertEquals("127.0.0.1", c.getQueueHost());
        assertEquals(MessageProcessorType.TYPE_A, c.getMessageProcessorType());
    }
}