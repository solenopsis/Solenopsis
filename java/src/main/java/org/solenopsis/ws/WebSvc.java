package com.redhat.solenopsis.ws;

/**
 *
 * Base interface for all services.
 *
 * @author sfloess
 *
 */
public interface WebSvc<P> {
    /**
     * Return the login service being used.
     */
    public SecurityWebSvc getSecurityWebSvc();  
    
    public P getPort() throws Exception;
}
