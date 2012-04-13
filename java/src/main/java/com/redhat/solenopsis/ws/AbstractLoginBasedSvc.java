/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.redhat.solenopsis.ws;

import java.util.logging.Level;

/**
 *
 * The purpose of this class is
 *
 * @author sfloess
 *
 */
public abstract class AbstractLoginBasedSvc extends AbstractSvc {
    private final Credentials credentials;
    private final String securityPassword;
    private final String svcUrl;
    
    protected Credentials getCredentials() {
        return credentials;
    }
    
    protected String getSecurityPassword() {
        return securityPassword;
    }
    
    protected String getSvcUrl() {
        return svcUrl;
    }
    
    /**
     * Set up the credentials.
     * 
     * @param credentials 
     */
    protected AbstractLoginBasedSvc(final Credentials credentials, final String urlSuffix) {
        this.credentials      = credentials;
        this.securityPassword = computeWebServicePassword(credentials);
        this.svcUrl           = credentials.getUrl() + "/" + urlSuffix + "/" + credentials.getApiVersion();
    }

    /**
     * Compute the web service password - this is a combination of <code>password</code>
     * plus <code>token</code>.
     *
     * @param password the password.
     * @param token the security token.
     *
     * @return the web service password (combination of password plus token).
     */
    public static String computeWebServicePassword(final Credentials credentials) {
        return credentials.getPassword() + credentials.getToken();
    }
    
    /**
     * Request a login.
     */
    public abstract void login() throws Exception;
}
