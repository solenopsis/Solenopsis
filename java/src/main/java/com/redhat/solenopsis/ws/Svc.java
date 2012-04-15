package com.redhat.solenopsis.ws;

/**
 *
 * Base interface for all services.
 *
 * @author sfloess
 *
 */
public interface Svc<P> {
    /**
     * Request a new login.
     * 
     * Returns the URL of the service.
     * 
     * @throws Exception if any problems arise logging in.
     */
    public void login() throws Exception;

    public boolean isLoggedIn();
    
    public P getPort() throws Exception;
}
