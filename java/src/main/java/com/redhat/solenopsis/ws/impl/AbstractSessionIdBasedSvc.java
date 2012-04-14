package com.redhat.solenopsis.ws.impl;

import com.redhat.solenopsis.ws.LoginSvc;
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
     * The service we use to login.
     */
    private final LoginSvc loginSvc;
    
    /**
     * Return the login service.
     */
    protected LoginSvc getLoginSvc() {
        return loginSvc;
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
    
    @Override
    protected void doLogin() throws Exception {
        getLoginSvc().login();
        setSessionId(getBindingProvider(), getLoginSvc().getSessionId());
    }

    protected AbstractSessionIdBasedSvc(final LoginSvc loginSvc, final String wsdlResource, final String urlSuffix) throws Exception {
        super(wsdlResource, loginSvc.getServerUrl() + "/" + urlSuffix);
        this.loginSvc = loginSvc;
    }
}
