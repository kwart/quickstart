package org.jboss.as.quickstarts.jms;

import java.util.logging.Level;

import javax.jms.JMSContext;
import javax.jms.JMSProducer;
import javax.naming.NamingException;

public class ProducerClient extends AbstractJMSClient {

    public ProducerClient() throws NamingException {
        super();
    }

    @Override
    public void justDoIt(JMSContext context) {
        log.info("Sending messages with content: " + content);
        // Send the specified number of messages
        JMSProducer producer = context.createProducer();
        int i = 0;
        while (true) {
            i++;
            String body = i + "\t" + content;
            producer.send(destination, body);
            System.out.println("Sent: " + body);
            try {
                Thread.sleep(1000L * waitTimeSec);
            } catch (InterruptedException e) {
                log.log(Level.INFO, "Waiting interrupted", e);
            }
        }
    }

}
