package org.jboss.as.quickstarts.ejb.remote.client;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.jboss.as.quickstarts.ejb.remote.stateless.RemoteHello;

/**
 * A sample program which acts a remote client for a EJB deployed on EAP server.
 */
public class RemoteEJBClient {

    public static void main(String[] args) throws Exception {
        final Hashtable<String, String> jndiProperties = new Hashtable<>();
        jndiProperties.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
        final Context context = new InitialContext(jndiProperties);

        final RemoteHello remoteHello = (RemoteHello) context
                .lookup("ejb:/wildfly-ejb-remote-server-side/HelloBean!" + RemoteHello.class.getName());
        for (int i = 1; i <= 10; i++) {
            boolean isMessageSent = remoteHello.sendMessage("Test message");
            System.out.println(i + ". isMessageSent = " + isMessageSent);
        }
    }
}
