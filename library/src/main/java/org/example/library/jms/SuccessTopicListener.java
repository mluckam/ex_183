package org.example.library.jms;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.jboss.logging.Logger;

/**
 * Message-Driven Bean implementation class for: SuccessTopicListener
 */
@MessageDriven(activationConfig = {
        @ActivationConfigProperty(propertyName = "destination", propertyValue = JmsConnection.SUCCESS_TOPIC),
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic") }, mappedName = JmsConnection.SUCCESS_TOPIC)
public class SuccessTopicListener implements MessageListener {

    private static final Logger LOG = Logger.getLogger(SuccessTopicListener.class);

    public void onMessage(Message message) {
        TextMessage textMessage = TextMessage.class.cast(message);
        try {
            LOG.info(textMessage.getText());
        } catch (JMSException e) {
            LOG.error("", e);
        }

    }

}
