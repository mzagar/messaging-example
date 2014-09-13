package messaging.gateway.servlet;

import messaging.gateway.message.Message;
import messaging.gateway.message.MessageFactory;
import messaging.gateway.message.MessageValidationException;
import messaging.gateway.message.MessageValidator;
import messaging.gateway.queue.MessageQueuePublisher;
import messaging.gateway.queue.MessageQueuePublisherFactory;
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
 * Message gateway servlet - receives and publishes to message queue messages received in json format according
 * to specification in body of http POST request.<p>
 *
 * Parameters 'queueName' and 'queueHost' are mandatory and must be specified as init-params.<p>
 *
 * Created by mzagar on 10.9.2014.
 */
public class MessageGatewayServlet extends HttpServlet {
    private Logger logger = LoggerFactory.getLogger(MessageGatewayServlet.class);

    private static final String PARAM_QUEUE_NAME = "queueName";
    private static final String PARAM_QUEUE_HOST = "queueHost";

    private MessageFactory messageFactory;
    private MessageValidator messageValidator;
    private MessageQueuePublisher messagePublisher;

    @Override
    public void init() throws ServletException {
        String queueHost = getInitParameter(PARAM_QUEUE_HOST);
        if (queueHost == null || queueHost.isEmpty()) {
            throw new ServletException("init parameter '" + PARAM_QUEUE_HOST + "' is mandatory");
        }

        String queueName = getInitParameter(PARAM_QUEUE_NAME);
        if (queueName == null || queueName.isEmpty()) {
            throw new ServletException("init parameter '" + PARAM_QUEUE_NAME + "' is mandatory");
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
        logger.debug("Start HTTP request, src={}:{}", req.getRemoteAddr(), req.getRemotePort());

        String json = inputStreamToString(req.getInputStream());
        logger.debug("json: {}", json);

        Message message;

        try {
            message = messageFactory.createFromJson(json);
            logger.debug("message: {}", message);
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
            logger.error("Message publish failed: " + e.getMessage(), e);
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return;
        }

        response(resp, "OK", HttpServletResponse.SC_OK);

        logger.debug("End HTTP request, src={}:{}", req.getRemoteAddr(), req.getRemotePort());
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

