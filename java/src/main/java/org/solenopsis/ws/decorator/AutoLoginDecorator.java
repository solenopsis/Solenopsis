package com.redhat.solenopsis.ws.decorator;

import com.redhat.solenopsis.ws.AbstractWebSvcDecorator;
import com.redhat.solenopsis.ws.WebSvc;

/**
 *
 * If a session 
 *
 * @author sfloess
 *
 */
public class AutoLoginDecorator<P> extends AbstractWebSvcDecorator<P> {
    
    public AutoLoginDecorator(WebSvc<P> decoratee) {
        super(decoratee);
    }

    @Override
    public P getPort() throws Exception {
        if (!getDecoratee().getSecurityWebSvc().isLoggedIn()) {
            getDecoratee().getSecurityWebSvc().login();
        }
        
        return getDecoratee().getPort();
    }
}
