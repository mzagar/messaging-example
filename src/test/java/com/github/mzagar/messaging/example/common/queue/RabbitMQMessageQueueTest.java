package com.github.mzagar.messaging.example.common.queue;

import com.github.mzagar.messaging.example.common.message.Message;
import com.github.mzagar.messaging.example.common.message.MessageData;
import com.github.mzagar.messaging.example.common.queue.rabbitmq.AbstractRabbitMQMessageQueue;
import com.github.mzagar.messaging.example.common.queue.rabbitmq.RabbitMQMessageQueueProducer;
import org.junit.Test;

public class RabbitMQMessageQueueTest {

    @Test
    public void test() throws MessageQueueException {
        MessageQueueProducer p = new RabbitMQMessageQueueProducer("localhost", "messageQueue");
        p.start();
        for(int i = 0; i < 100; i++ )
        p.produce(new Message(i, 2, "3.4.5", new MessageData(6, 7)));
//        q.dequeue(new MessageProcessor() {
//            @Override
//            public void process(Message message) {
//                System.out.println(message);
//            }
//        });
        p.stop();
    }

}