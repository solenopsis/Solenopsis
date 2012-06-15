package org.solenopsis.ws.util;

import java.net.URL;
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
    
    /**
     * Taking the <code>rawURL</code>, will return the URL for just the server.
     * 
     * @param rawUrl The complete raw URL (including any additional paths).
     * 
     * @return the server's URL including protocol.
     */
    public static String computeServerUrl(final String rawUrl) {
        try {
            final URL url = new URL(rawUrl);

            return url.getProtocol() + "://" + url.getHost();
        }
        
        catch (final Exception exception) {
            getLogger().log(Level.SEVERE, "Trouble getting protocol and host!", exception);
        }
        
        return "";
    }    
}
