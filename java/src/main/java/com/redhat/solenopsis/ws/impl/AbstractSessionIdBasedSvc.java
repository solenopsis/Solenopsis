package com.redhat.solenopsis.ws.impl;

import com.redhat.solenopsis.ws.LoginSvc;
import com.redhat.solenopsis.ws.ServiceTypeEnum;
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
public abstract class AbstractSessionIdBasedSvc<P> extends AbstractSvc<P> {    
    /**
     * The service we use to login.
     */
    private final LoginSvc loginSvc;
    
    /**
     * Our port.
     */
    private P port;
    
    /**
     * Return the login service.
     */
    protected final LoginSvc getLoginSvc() {
        return loginSvc;
    }
    
    /**
     * @{inheritDoc}
     */
    @Override
    protected final String getUrl() {
        return getLoginSvc().getCredentials().getUrl();
    }
    
    /**
     * Set the session id.
     */
    protected void setSessionId(BindingProvider bindingProvider, String sessionId) {
        final SessionIdInjectHandler handler = new SessionIdInjectHandler(sessionId);
        final List<Handler> handlerChain = new ArrayList<Handler>();
        
        handlerChain.add(handler);
        
        bindingProvider.getBinding().setHandlerChain(handlerChain);
        
        if (getLogger().isLoggable(Level.INFO)) {
            getLogger().log(Level.INFO, "Seting session id to [{0}]", sessionId);
        }
    }
    
    /**
     * @{@inheritDoc}
     */
    @Override
    public P getPort() throws Exception {
        if (!isLoggedIn() || null == port) {            
            login();
            
            port = createPort();
            
            setUrl((BindingProvider) port, getUrl() + "/" + getServiceType().getUrlSuffix() + "/" + getServiceName());
            setSessionId((BindingProvider) port, getLoginSvc().getSessionId());                                                        
        }
        
        return port;
    }
    
    protected AbstractSessionIdBasedSvc(final ServiceTypeEnum serviceType, final LoginSvc loginSvc) {
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
