package messaging.gateway.servlet;

import messaging.gateway.message.Message;
import messaging.gateway.message.MessageFactory;
import messaging.gateway.queue.MessageQueuePublisherFactory;
import messaging.gateway.queue.MessageQueuePublisher;
import messaging.gateway.validator.MessageValidationException;
import messaging.gateway.validator.MessageValidator;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

/**
 * Created by mzagar on 10.9.2014.
 */
public class MessageGatewayServlet extends HttpServlet {
    private Logger logger = LoggerFactory.getLogger(MessageGatewayServlet.class);

    private MessageFactory messageFactory;
    private MessageValidator messageValidator;
    private MessageQueuePublisher messagePublisher;

    @Override
    public void init() throws ServletException {
        String queueHost = getInitParameter("queueHost");
        if (queueHost == null || queueHost.isEmpty()) {
            throw new ServletException("init parameter 'queueHost' is mandatory");
        }

        String queueName = getInitParameter("queueName");
        if (queueName == null || queueName.isEmpty()) {
            throw new ServletException("init parameter 'queueName' is mandatory");
        }

        messageFactory = new MessageFactory();
        messageValidator = new MessageValidator();

        messagePublisher = new MessageQueuePublisherFactory().createPublisher(queueHost, queueName);
        messagePublisher.start();
    }

    @Override
    public void destroy() {
        messagePublisher.stop();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String json = inputStreamToString(req.getInputStream());

        if (json == null || json.isEmpty()) {
            logger.debug("empty json body");
            response(resp, "expecting message json body, but body is empty", HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        Message message;

        try {
            message = messageFactory.createFromJson(json);
        } catch(Exception e) {
            logger.error(e.getMessage(), e);
            response(resp, e.getMessage(), HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        try {
            messageValidator.validate(message);
        } catch (MessageValidationException e) {
            logger.warn("Invalid message: {}: {}", e.getMessage(), message);
            response(resp, e.getMessage(), HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        try {
            messagePublisher.publish(message);
        } catch (Exception e) {
            logger.error("message publish failed: " + e.getMessage(), e);
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return;
        }

        resp.setStatus(HttpServletResponse.SC_OK);
    }

    private void response(HttpServletResponse resp, String message, int statusCode) throws IOException {
        resp.getOutputStream().print(message);
        resp.setStatus(statusCode);
    }

    private String inputStreamToString(InputStream inputStream) throws IOException {
        StringWriter writer = new StringWriter();
        IOUtils.copy(inputStream, writer);
        return writer.toString();
    }
}

