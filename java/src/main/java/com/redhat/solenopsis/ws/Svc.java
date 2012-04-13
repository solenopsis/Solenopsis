package com.redhat.solenopsis.ws;

/**
 *
 * Base interface for all services.
 *
 * @author sfloess
 *
 */
public interface Svc {
    /**
     * Request a new login.
     * 
     * @throws Exception if any problems arise logging in.
     */
    public void login() throws Exception;
}
