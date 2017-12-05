/*
 * JBoss, Home of Professional Open Source
 * Copyright 2015, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jboss.as.quickstarts.jms;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSContext;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public abstract class AbstractJMSClient implements Runnable {

    protected final Logger log = Logger.getLogger(AbstractJMSClient.class.getName());

    // Set up all the default values
    private static final String DEFAULT_MESSAGE = "Hello, World!";
    private static final String DEFAULT_CONNECTION_FACTORY = "jms/RemoteConnectionFactory";
    private static final String DEFAULT_DESTINATION = "jms/queue/test";
    private static final String DEFAULT_WAIT_TIME_SEC = "1";
    private static final String DEFAULT_USERNAME = "guest";
    private static final String DEFAULT_PASSWORD = "guest";
    private static final String INITIAL_CONTEXT_FACTORY = "org.wildfly.naming.client.WildFlyInitialContextFactory";
    private static final String PROVIDER_URL = "http-remoting://127.0.0.1:8080";

    Context namingContext = null;
    ConnectionFactory connectionFactory;
    Destination destination;
    int waitTimeSec;
    String content;
    String userName;
    String password;

    public AbstractJMSClient() throws NamingException {
        userName = System.getProperty("username", DEFAULT_USERNAME);
        password = System.getProperty("password", DEFAULT_PASSWORD);

        // Set up the namingContext for the JNDI lookup
        final Properties env = new Properties();
        env.put(Context.INITIAL_CONTEXT_FACTORY, INITIAL_CONTEXT_FACTORY);
        env.put(Context.PROVIDER_URL, System.getProperty(Context.PROVIDER_URL, PROVIDER_URL));
        env.put(Context.SECURITY_PRINCIPAL, userName);
        env.put(Context.SECURITY_CREDENTIALS, password);
        log.info("Using following properties to create InitialContext " + env);
        namingContext = new InitialContext(env);

        // Perform the JNDI lookups
        String connectionFactoryString = System.getProperty("connection.factory", DEFAULT_CONNECTION_FACTORY);
        log.info("Attempting to acquire connection factory \"" + connectionFactoryString + "\"");
        connectionFactory = (ConnectionFactory) namingContext.lookup(connectionFactoryString);
        log.info("Found connection factory \"" + connectionFactoryString + "\" in JNDI");

        String destinationString = System.getProperty("destination", DEFAULT_DESTINATION);
        log.info("Attempting to acquire destination \"" + destinationString + "\"");
        destination = (Destination) namingContext.lookup(destinationString);
        log.info("Found destination \"" + destinationString + "\" in JNDI");

        waitTimeSec = Integer.parseInt(System.getProperty("wait.time", DEFAULT_WAIT_TIME_SEC));
        content = System.getProperty("message.content", DEFAULT_MESSAGE);
    }

    public void run() {
        try (JMSContext context = connectionFactory.createContext(userName, password)) {
            justDoIt(context);
        }
        try {
            namingContext.close();
        } catch (NamingException e) {
            log.log(Level.SEVERE, "Closing naming context failed", e);
        }
    }

    public abstract void justDoIt(JMSContext context);
}
