package org.example.library.jms;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.jboss.logging.Logger;

@MessageDriven(activationConfig = {
        @ActivationConfigProperty(propertyName = "destination", propertyValue = JmsConnection.SUCCESS_QUEUE),
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue") }, mappedName = JmsConnection.SUCCESS_QUEUE)
public class SuccessQueueListener implements MessageListener {

    private static final Logger LOG = Logger.getLogger(SuccessQueueListener.class);

    public void onMessage(Message message) {
        TextMessage textMessage = TextMessage.class.cast(message);
        try {
            LOG.info(textMessage.getText());
        } catch (JMSException e) {
            LOG.error("", e);
        }

    }

}
