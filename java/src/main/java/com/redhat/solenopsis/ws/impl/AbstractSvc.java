package com.redhat.solenopsis.ws.impl;

import com.redhat.solenopsis.ws.Svc;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.ws.BindingProvider;

/**
 *
 * Abstract base class for all services.
 *
 * @author sfloess
 *
 */
public abstract class AbstractSvc<P> implements Svc<P> {
    /**
     * Our logger.
     */
    private final Logger logger;
    
    /**
     * Holds the port for the web service call.
     */
    private P port;

    /**
     * Return the logger.
     */
    protected Logger getLogger() {
        return logger;
    }
    
    /**
     * Create a port for web service calls if needed.
     * 
     * @return a port for web service calls. 
     */
    protected abstract P createPort();
    
    /**
     * Return the URL for our service.
     */
    protected abstract String getServiceUrl();
    
    /**
     * Set the URL to call on the web service.
     */
    protected void setUrl(BindingProvider bindingProvider, String svcUrl) {
        bindingProvider.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, svcUrl);

        if (getLogger().isLoggable(Level.INFO)) {
            getLogger().log(Level.INFO, "Seting URL to [{0}]", svcUrl);
        }
    }
    
    /**
     * @{@inheritDoc}
     */
    @Override
    public P getPort() throws Exception {
        if (!isLoggedIn()) {
            login();
                    
            port = createPort();
            
            setUrl((BindingProvider) port, getServiceUrl());
        }
        
        return port;
    }
    
    protected AbstractSvc() {
        this.logger = Logger.getLogger(getClass().getName());
    }
}
