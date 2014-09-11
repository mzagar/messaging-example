package com.github.mzagar.messaging.example.servlet;


import com.github.mzagar.messaging.example.common.message.Message;
import com.github.mzagar.messaging.example.common.message.MessageFactory;
import com.github.mzagar.messaging.example.common.queue.MessageQueueFactory;
import com.github.mzagar.messaging.example.common.queue.MessageQueueProducer;
import org.apache.commons.io.IOUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

/**
 * Created by mzagar on 10.9.2014.
 */
@WebServlet(name="message-gateway-servlet", urlPatterns = {"/hello2"})
public class MessageGatewayServlet extends HttpServlet {

    private final MessageFactory messageFactory = new MessageFactory();
    private final MessageQueueProducer messageQueue = new MessageQueueFactory().createProducer("localhost", "messageQueue");

    @Override
    public void init() throws ServletException {
        messageQueue.start();
    }

    @Override
    public void destroy() {
        messageQueue.stop();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String json = inputStreamToString(req.getInputStream());

        if (json == null || json.isEmpty()) {
            resp.getOutputStream().print("expecting message json body, but body is empty");
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        Message message;

        try {
            message = messageFactory.createFromJson(json);
        } catch(Exception e) {
            resp.getOutputStream().print(e.getMessage());
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        try {
            messageQueue.produce(message);
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return;
        }

        resp.setStatus(HttpServletResponse.SC_OK);
    }

    private String inputStreamToString(InputStream inputStream) throws IOException {
        StringWriter writer = new StringWriter();
        IOUtils.copy(inputStream, writer);
        return writer.toString();
    }
}

