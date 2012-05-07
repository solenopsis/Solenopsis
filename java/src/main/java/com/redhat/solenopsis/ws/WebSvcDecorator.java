package com.redhat.solenopsis.ws;

/**
 *
 * Base interface for session based web services.
 *
 * @author sfloess
 *
 */
public interface WebSvcDecorator<P> extends WebSvc<P> {
    /**
     * Return the login service being used.
     */
    public WebSvc<P> getDecoratee();    
}
