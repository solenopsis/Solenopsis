package com.redhat.solenopsis.ws.util;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.Handler;

/**
 *
 * Web service utility class.
 *
 * @author sfloess
 *
 */
public class WebServiceUtil { 
    /**
     * Our logger.
     */
    private static final Logger logger = Logger.getLogger(WebServiceUtil.class.getName());
    
    /**
     * Return the logger.
     * 
     * @return the logger.
     */
    private static Logger getLogger() {
        return logger;
    }
    
    /**
     * Set the URL to call on the web service.
     */
    public static void setUrl(BindingProvider bindingProvider, String svcUrl) {
        bindingProvider.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, svcUrl);

        if (getLogger().isLoggable(Level.INFO)) {
            getLogger().log(Level.INFO, "Seting URL to [{0}]", svcUrl);
        }
    }
    
    /**
     * Set the session id.
     */
    public static void setSessionId(BindingProvider bindingProvider, String sessionId) {
        final SessionIdInjectHandler handler = new SessionIdInjectHandler(sessionId);
        final List<Handler> handlerChain = new ArrayList<Handler>();
        
        handlerChain.add(handler);
        
        bindingProvider.getBinding().setHandlerChain(handlerChain);
        
        if (getLogger().isLoggable(Level.INFO)) {
            getLogger().log(Level.INFO, "Seting session id to [{0}]", sessionId);
        }
    }
}
