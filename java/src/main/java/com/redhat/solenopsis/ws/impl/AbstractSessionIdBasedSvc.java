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
    private void setSessionId() {
        final LoginInjectHandler handler = new LoginInjectHandler(getLoginSvc().getSessionId());
        final List<Handler> handlerChain = new ArrayList<Handler>();
        
        handlerChain.add(handler);
        
        getBindingProvider().getBinding().setHandlerChain(handlerChain);
        
        if (getLogger().isLoggable(Level.INFO)) {
            getLogger().log(Level.INFO, "Seting session id to [{0}]", getLoginSvc().getSessionId());
        }
    }
    
    @Override
    public void login() throws Exception {
        getLoginSvc().login();        
        setSessionId();
        setUrl(getBindingProvider(), getLoginSvc().getSvcUrl());
    }

    protected AbstractSessionIdBasedSvc(final LoginSvc loginSvc) {
        this.loginSvc = loginSvc;
    }
}
