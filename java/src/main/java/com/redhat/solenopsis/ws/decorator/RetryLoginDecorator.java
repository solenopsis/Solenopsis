package com.redhat.solenopsis.ws.decorator;

import com.redhat.solenopsis.ws.AbstractWebSvcDecorator;
import com.redhat.solenopsis.ws.WebSvc;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 *
 * If a session 
 *
 * @author sfloess
 *
 */
public class RetryLoginDecorator<P> extends AbstractWebSvcDecorator<P> {
    private class PortInvoker<P> implements InvocationHandler {
        private final P wrapper;
        
        PortInvoker (final P wrapper) {
            this.wrapper = wrapper;
        }
        
        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            Object retVal = null;                        
            
            try {
                retVal = method.invoke(wrapper, args);
            }
            
            catch(InvocationTargetException exception) {
                if (exception.getTargetException().getMessage().contains("INVALID_SESSION_ID")) {
                    getDecoratee().getSecurityWebSvc().login();
                    getDecoratee().getPort();
                    retVal = method.invoke(wrapper, args);                                                        
                }
                else {
                    throw exception;
                }
            }
            
            return retVal;
        }
    }
    
    public RetryLoginDecorator(WebSvc<P> decoratee) {
        super(decoratee);
    }

    @Override
    public P getPort() throws Exception {
        if (!getDecoratee().getSecurityWebSvc().isLoggedIn()) {
            getDecoratee().getSecurityWebSvc().login();
        }
        
        return (P) (Proxy.newProxyInstance(getClass().getClassLoader(), getDecoratee().getPort().getClass().getInterfaces(), new PortInvoker(getDecoratee().getPort()))); 
    }
}
