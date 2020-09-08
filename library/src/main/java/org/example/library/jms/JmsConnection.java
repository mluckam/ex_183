package org.example.library.jms;

public final class JmsConnection {

    private JmsConnection() {
    }

    public static final String REMOTE_CONNECTION_FACTORY = "java:comp/RemoteConnectionFactory";
    public static final String SUCCESS_QUEUE = "java:/jms/queue/successQueue";
    public static final String SUCCESS_TOPIC = "java:/jms/topic/successTopic";
}
