package com.redhat.solenopsis.ws;

import com.redhat.sforce.soap.enterprise.LoginResult;
import com.redhat.sforce.soap.metadata.MetadataPortType;
import com.redhat.sforce.soap.metadata.MetadataService;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.Handler;

/**
 *
 * Manages 
 *
 * @author sfloess
 *
 */
public class Metadata {
    /**
     * Our logger.
     */
    private static final Logger logger = Logger.getLogger(Metadata.class.getName());
    
    /**
     * Return the logger.
     */
    protected static Logger getLogger() {
        return logger;
    }
    
    /**
     * Set the URL to call on the web service.
     * 
     * @param port is the web service port.
     * @param url is the URL for the call.
     */
    protected static void setUrl(final MetadataPortType port, final String url) {
        final BindingProvider bp = (BindingProvider) port;
        bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, url);
        
        if (getLogger().isLoggable(Level.INFO)) {
            getLogger().log(Level.INFO, "Seting URL to [{0}]", url);
        }
    }
    
    /**
     * Set the session id.
     * 
     * @param port is the web service port.
     * @param sessionId is the session id to set in the SOAP header.
     */
    protected static void setSessionId(final MetadataPortType port, final String sessionId) {        
        final BindingProvider bp = (BindingProvider) port;
        final LoginInjectHandler handler = new LoginInjectHandler(sessionId);
        final List<Handler> handlerChain = new ArrayList<Handler>();
        handlerChain.add(handler);
        
        bp.getBinding().setHandlerChain(handlerChain);  
                
        if (getLogger().isLoggable(Level.INFO)) {
            getLogger().log(Level.INFO, "Seting session id to [{0}]", sessionId);
        }
    }
    
    /**
     * Return the port.
     */
    public static MetadataPortType getPort(final String sessionId, final String url) throws Exception {
        final URL wsdlUrl = Metadata.class.getResource(ServiceEnum.METADATA_SERVICE.getWsdlResource());
        if (wsdlUrl == null) {
            throw new IllegalArgumentException("Could not find WSDL at "+ ServiceEnum.METADATA_SERVICE.getWsdlResource());
        }
        
        MetadataService service = new MetadataService(wsdlUrl, ServiceEnum.METADATA_SERVICE.getQName());    
        MetadataPortType port = service.getMetadata();

        setSessionId(port, sessionId);
        setUrl(port, url + "/Metadata");      
        
        return port;   
    }
    
    
    /**
     * Return the port.
     */
    public static MetadataPortType getPort(final LoginResult loginResult) throws Exception {        
        final String url = loginResult.getMetadataServerUrl();
        
        return getPort(loginResult.getSessionId(), url.substring(0, url.lastIndexOf('/')));   
    }     
}
