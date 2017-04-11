package org.jboss.as.quickstarts.ejb.remote.stateless;

public interface RemoteHello {

    boolean sendMessage(String text) throws Exception;
}
