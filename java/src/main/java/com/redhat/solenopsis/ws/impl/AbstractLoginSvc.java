/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.redhat.solenopsis.ws.impl;

import com.redhat.solenopsis.ws.Credentials;
import com.redhat.solenopsis.ws.LoginSvc;
import com.redhat.solenopsis.ws.util.CredentialsUtil;

/**
 *
 * The purpose of this class is
 *
 * @author sfloess
 *
 */
public abstract class AbstractLoginSvc extends AbstractSvc implements LoginSvc {
    private final Credentials credentials;
    private final String securityPassword;
    private final String svcUrl;

    /**
     * Set up the credentials.
     * 
     * @param credentials 
     */
    protected AbstractLoginSvc(final Credentials credentials, final String urlSuffix) {
        this.credentials      = credentials;
        this.securityPassword = CredentialsUtil.computeWebServicePassword(credentials);
        this.svcUrl           = credentials.getUrl() + "/" + urlSuffix + "/" + credentials.getApiVersion();
    }
    
    @Override
    public Credentials getCredentials() {
        return credentials;
    }
    
    @Override
    public String getSecurityPassword() {
        return securityPassword;
    }
    
    @Override
    public String getSvcUrl() {
        return svcUrl;
    }    
}
