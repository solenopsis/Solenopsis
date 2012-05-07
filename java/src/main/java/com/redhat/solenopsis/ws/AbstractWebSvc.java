package com.redhat.solenopsis.ws;

import com.redhat.solenopsis.ws.util.WebServiceUtil;
import java.util.logging.Logger;
import javax.xml.ws.BindingProvider;

/**
 *
 * Abstract base class for all services.
 *
 * @author sfloess
 *
 */
public abstract class AbstractWebSvc<S, P> implements WebSvc<P> {
    /**
     * Our logger.
     */
    private final Logger logger;
    
    /**
     * The service that holds the port.
     */
    private final S service;
        
    /**
     * Our port.
     */
    private P port;
    
    /**
     * The name of the SFDC web service.
     */
    private final String webServiceName;
    
    /**
     * The type of SFDC web service.
     */
    private final WebServiceTypeEnum webServiceType;
    
    private final SecurityWebSvc securityWebSvc;

    /**
     * Return the logger.
     */
    protected final Logger getLogger() {
        return logger;
    }   
    
    protected final String getWebServiceName() {
        return webServiceName;
    }   

    protected final WebServiceTypeEnum getWebServiceType() {
        return webServiceType;
    }
    
    protected final S getService() {
        return service;
    } 
    
    /**
     * Create a port for web service calls if needed.
     * 
     * @return a port for web service calls. 
     */
    protected abstract P createPort();
    
    protected AbstractWebSvc(final S service, final String webServiceName, final WebServiceTypeEnum webServiceType, final SecurityWebSvc securityWebSvc) {
        this.logger         = Logger.getLogger(getClass().getName());
        this.service        = service;
        this.webServiceName = webServiceName;
        this.webServiceType = webServiceType;
        this.securityWebSvc = securityWebSvc;
    } 
    
    /**
     * @{@inheritDoc}
     */
    @Override
    public P getPort() throws Exception { 
        if (port == null) {
            port = createPort();
        }
        
        //
        // Just in case the login service is being shared and a new login
        // was requested...
        //
        WebServiceUtil.setUrl((BindingProvider) port, getSecurityWebSvc().getServerUrl() + "/" + getWebServiceType().getUrlSuffix() + "/" + getWebServiceName());
        WebServiceUtil.setSessionId((BindingProvider) port, getSecurityWebSvc().getSessionId());                                                        
        return port;
    }
    

    @Override
    public SecurityWebSvc getSecurityWebSvc() {
        return securityWebSvc;
    }           
}
