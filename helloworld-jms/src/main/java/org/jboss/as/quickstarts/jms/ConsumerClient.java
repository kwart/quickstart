package org.jboss.as.quickstarts.jms;

import java.util.logging.Level;

import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.naming.NamingException;

public class ConsumerClient extends AbstractJMSClient {

    public ConsumerClient() throws NamingException {
        super();
    }

    @Override
    public void justDoIt(JMSContext context) {
        log.info("Starting Receive messages");
        // Create the JMS consumer
        try (JMSConsumer consumer = context.createConsumer(destination)) {
            while (true) {
                String text = consumer.receiveBody(String.class, 1000L * waitTimeSec);
                if (text != null) {
                    System.out.println("Received: " + text);
                    try {
                        Thread.sleep(1000 * waitTimeSec);
                    } catch (InterruptedException e) {
                        log.log(Level.INFO, "Waiting interrupted", e);
                    }
                } else {
                    System.out.print(".");
                }
            }
        }
    }

}
