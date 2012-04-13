package com.redhat.solenopsis.ws;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.Handler;

/**
 *
 * Base class for any SFDC web service that needs the session id in the header.
 *
 * @author sfloess
 *
 */
public abstract class AbstractSessionIdBasedSvc extends AbstractSvc {

    /**
     * Set the session id.
     *
     * @param port is the web service port.
     * @param sessionId is the session id to set in the SOAP header.
     */
    protected void setSessionId(final BindingProvider bindingProvider, final String sessionId) {
        final LoginInjectHandler handler = new LoginInjectHandler(sessionId);
        final List<Handler> handlerChain = new ArrayList<Handler>();
        
        handlerChain.add(handler);
        
        bindingProvider.getBinding().setHandlerChain(handlerChain);
        
        if (getLogger().isLoggable(Level.INFO)) {
            getLogger().log(Level.INFO, "Seting session id to [{0}]", sessionId);
        }
    }

    AbstractSessionIdBasedSvc() {
    }
}
