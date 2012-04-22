package com.redhat.solenopsis.ws.impl;

import com.redhat.solenopsis.ws.LoginWebSvc;
import com.redhat.solenopsis.ws.WebServiceTypeEnum;
import com.redhat.solenopsis.ws.util.WebServiceUtil;
import javax.xml.ws.BindingProvider;

/**
 *
 * Base class for any SFDC web service that needs the session id in the header.
 *
 * @author sfloess
 *
 */
public abstract class AbstractSessionIdBasedWebSvc<P> extends AbstractWebSvc<P> {    
    /**
     * The service we use to login.
     */
    private final LoginWebSvc loginSvc;
    
    /**
     * Our port.
     */
    private P port;
    
    /**
     * Return the login service.
     */
    protected final LoginWebSvc getLoginSvc() {
        return loginSvc;
    }
    
    /**
     * @{inheritDoc}
     */
    @Override
    protected final String getUrl() {
        return getLoginSvc().getServerUrl();
    }
    
    /**
     * @{@inheritDoc}
     */
    @Override
    public P getPort() throws Exception {
        if (!isLoggedIn() || null == port) {            
            login();
            
            port = createPort();
        }
        
        //
        // Just in case the login service is being shared and a new login
        // was requested...
        //
        WebServiceUtil.setUrl((BindingProvider) port, getUrl() + "/" + getServiceType().getUrlSuffix() + "/" + getServiceName());
        WebServiceUtil.setSessionId((BindingProvider) port, getLoginSvc().getSessionId());                                                        
        return port;
    }
    
    protected AbstractSessionIdBasedWebSvc(final WebServiceTypeEnum serviceType, final LoginWebSvc loginSvc) {
        super(serviceType);
        
        this.loginSvc = loginSvc;
    }
    
    @Override
    public void login() throws Exception {
        getLoginSvc().login();
    }
    
    @Override
    public boolean isLoggedIn() {
        return getLoginSvc().isLoggedIn();
    }
}
