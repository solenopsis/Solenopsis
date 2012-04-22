package com.redhat.solenopsis.ws.impl;

import com.redhat.sforce.soap.enterprise.LoginResult;
import com.redhat.sforce.soap.enterprise.SforceService;
import com.redhat.sforce.soap.enterprise.Soap;
import com.redhat.solenopsis.credentials.Credentials;
import com.redhat.solenopsis.ws.WebServiceTypeEnum;
import java.util.logging.Level;

/**
 *
 * Manages 
 *
 * @author sfloess
 *
 */
public final class DefaultEnterpriseWebSvc extends AbstractLoginWebSvc<Soap> {   
    private final SforceService service;
    
    private LoginResult loginResult;
    
    protected SforceService getService() {
        return service;
    }

    
    protected LoginResult getLoginResult() {
        if (loginResult != null) {
            return loginResult;
        }
        
        throw new IllegalStateException("Please login!");
    }
    
    @Override
    protected Soap createPort() {                
        return getService().getSoap();
    }
    
    public DefaultEnterpriseWebSvc(final Credentials credentials) throws Exception {
        super(WebServiceTypeEnum.ENTERPRISE_SERVICE, credentials);
        
        this.service = new SforceService(WebServiceEnum.ENTERPRISE_SERVICE.getWsdlResource(), WebServiceEnum.ENTERPRISE_SERVICE.getQName());
    }

    @Override
    public String getMetadataServerUrl() {
        return getLoginResult().getMetadataServerUrl();
    }

    @Override
    public boolean isPasswordExpired() {
        return getLoginResult().isPasswordExpired();
    }

    @Override
    public boolean isSandbox() {
        return getLoginResult().isSandbox();
    }

    @Override
    public String getServerUrl() {
        return computeServerUrl(getLoginResult().getServerUrl());
    }

    @Override
    public String getSessionId() {
        return getLoginResult().getSessionId();
    }

    @Override
    public String getUserId() {
        return getLoginResult().getUserId();
    }

    @Override
    public void login() throws Exception {
        if (getLogger().isLoggable(Level.INFO)) {
            getLogger().log(Level.INFO, "User [{0}] Password [{1}]", new Object[]{getCredentials().getUserName(), getCredentials().getSecurityPassword()});
        }
        
        loginResult = null;
        loginResult = getPort().login(getCredentials().getUserName(), getCredentials().getSecurityPassword());
    }

    @Override
    public boolean isLoggedIn() {
        return loginResult != null;
    }
}
