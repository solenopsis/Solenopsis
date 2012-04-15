package com.redhat.solenopsis.ws.impl;

import com.redhat.solenopsis.ws.ServiceTypeEnum;
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
     * The type of SFDC web service.
     */
    private final ServiceTypeEnum serviceType;

    /**
     * Return the logger.
     */
    protected final Logger getLogger() {
        return logger;
    }
    
    /**
     * Return the service type.
     */
    protected final ServiceTypeEnum getServiceType() {
        return serviceType;
    }
     
    /**
     * Return the SFDC base url - for example https://test.salesforce.com or
     * https://login.salesforce.com
     */
    protected abstract String getUrl();
    
    /**
     * Return the service name.  For custom web services, its the
     * web service's name.  For metadata, enterprise or partner its the
     * API version.
     */
    protected abstract String getServiceName();
    
    /**
     * Create a port for web service calls if needed.
     * 
     * @return a port for web service calls. 
     */
    protected abstract P createPort();
    
    /**
     * Set the URL to call on the web service.
     */
    protected final void setUrl(BindingProvider bindingProvider, String svcUrl) {
        bindingProvider.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, svcUrl);

        if (getLogger().isLoggable(Level.INFO)) {
            getLogger().log(Level.INFO, "Seting URL to [{0}]", svcUrl);
        }
    }
    
    protected AbstractSvc(final ServiceTypeEnum serviceType) {
        this.logger      = Logger.getLogger(getClass().getName());
        this.serviceType = serviceType;
    }
}
