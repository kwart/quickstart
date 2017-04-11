package org.jboss.as.quickstarts.ejb.remote.stateless;

import javax.annotation.Resource;
import javax.ejb.Remote;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;

@Stateless
@Remote(RemoteHello.class)
public class HelloBean implements RemoteHello {

    public static final String SAYING = "Hello";
    public static final String QUEUE_NAME = "queue/TestQueue";
    public static final String QUEUE_NAME_JNDI = "java:jboss/exported/" + QUEUE_NAME;

    @Resource
    private SessionContext context;

    @Override
    public boolean sendMessage(String text) throws Exception {
        InitialContext initialContext = new InitialContext();

        try {
            ConnectionFactory cf = (ConnectionFactory) initialContext.lookup("java:/ConnectionFactory");
            Connection connection = null;

            try {
                Queue queue = cf.createContext("guest", "guest").createQueue(QUEUE_NAME);
                connection = cf.createConnection("guest", "guest");
                connection.start(); // we need to start connection for consumer

                Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
                MessageProducer sender = session.createProducer(queue);
                TextMessage message = session.createTextMessage("hello goodbye");
                sender.send(message);

                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            } finally {
                try {
                    if (connection != null)
                        connection.close();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        } finally {
            initialContext.close();
        }
    }

}
