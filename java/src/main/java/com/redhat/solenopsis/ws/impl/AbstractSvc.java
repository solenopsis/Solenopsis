package com.redhat.solenopsis.ws.impl;

import com.redhat.solenopsis.ws.Svc;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.ws.BindingProvider;

/**
 *
 * The purpose of this class is
 *
 * @author sfloess
 *
 */
public abstract class AbstractSvc implements Svc {
    /**
     * Our logger.
     */
    private final Logger logger;
    
    private final URL wsdlUrl;
    
    private final String svcUrl;
    
    private BindingProvider bindingProvider;

    /**
     * Return the logger.
     */
    protected Logger getLogger() {
        return logger;
    }
    
    protected URL getWsdlUrl() {
        return wsdlUrl;
    }
    
    protected String getSvcUrl() {
        return svcUrl;
    }
    
    protected BindingProvider getBindingProvider() {
        return bindingProvider;
    }

    /**
     * Set the URL to call on the web service.
     */
    protected void setUrl(BindingProvider bindingProvider, String svcUrl) {
        bindingProvider.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, svcUrl);
        if (getLogger().isLoggable(Level.INFO)) {
            getLogger().log(Level.INFO, "Seting URL to [{0}]", getSvcUrl());
        }
    }
    
    protected abstract BindingProvider createBindingProvider();
    
    protected abstract void doLogin() throws Exception;
    
    protected AbstractSvc(final String wsdlResource, final String svcUrl) throws Exception {
        this.logger  = Logger.getLogger(getClass().getName());
        this.wsdlUrl = getClass().getResource(wsdlResource);        
        this.svcUrl  = svcUrl;
        
        if (wsdlUrl == null) {
            logger.log(Level.SEVERE, "Cannot find WSDL " + wsdlResource);
            throw new IllegalArgumentException("Cannot find WSDL "+ wsdlResource);
        } 
    }
    
    public void login() throws Exception {
        bindingProvider = createBindingProvider();
        
        doLogin();
        
        setUrl(bindingProvider, getSvcUrl());
    }
}
