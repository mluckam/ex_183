package org.example.library.jms;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.jms.ConnectionFactory;
import javax.jms.JMSConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.Queue;
import javax.jms.Topic;

public class DestinationManager {

    @Resource(name = JmsConnection.REMOTE_CONNECTION_FACTORY)
    private ConnectionFactory remoteConnectionFactory;

    @Inject
    @JMSConnectionFactory(value = JmsConnection.REMOTE_CONNECTION_FACTORY)
    private JMSContext jmsContext;

    @Resource(name = JmsConnection.SUCCESS_QUEUE)
    private Queue successQueue;

    @Resource(name = JmsConnection.SUCCESS_TOPIC)
    private Topic successTopic;

    public DestinationManager() {
    }

    public void sendToSuccessQueue(String textMessage) {
        jmsContext.createProducer().send(successQueue, textMessage);
        
    }

    public void sendToSuccessTopic(String textMessage) {
        jmsContext.createProducer().send(successTopic, textMessage);
    }
}
