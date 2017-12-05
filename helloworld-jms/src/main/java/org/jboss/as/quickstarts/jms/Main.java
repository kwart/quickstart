package org.jboss.as.quickstarts.jms;

import javax.naming.NamingException;

public class Main {

    public static void main(String[] args) throws NamingException {
        Runnable task = null;
        if (args != null && args.length == 1) {
            if ("-producer".equals(args[0])) {
                task = new ProducerClient();
            } else if ("-consumer".equals(args[0])) {
                task = new ConsumerClient();
            }
        }
        if (task != null) {
            task.run();
        } else {
            System.err.println("Usage:");
            System.err.println("\tjava -jar helloworld-jms.jar [-producer|-consumer]");
            System.err.println();
            System.exit(1);
        }
    }
}
