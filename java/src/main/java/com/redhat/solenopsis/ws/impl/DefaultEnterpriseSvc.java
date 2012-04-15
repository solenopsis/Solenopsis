package com.redhat.solenopsis.ws.impl;

import com.redhat.solenopsis.ws.*;
import com.redhat.sforce.soap.enterprise.*;

/**
 *
 * Manages 
 *
 * @author sfloess
 *
 */
public final class DefaultEnterpriseSvc extends AbstractSvc<Soap> implements LoginSvc<Soap> {   
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
    protected String getServiceUrl() {
        return "";
    }
    
    @Override
    protected Soap createPort() {                
        return getService().getSoap();
    }
    
    public DefaultEnterpriseSvc(final Credentials credentials) throws Exception {
        service = new SforceService(ServiceEnum.ENTERPRISE_SERVICE.getWsdlResource(), ServiceEnum.ENTERPRISE_SERVICE.getQName());
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
        return getLoginResult().getServerUrl();
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
        loginResult = null;
        loginResult = createPort().login(getCredentials().getUserName(), getSecurityPassword());
    }

    @Override
    public boolean isLoggedIn() {
        return getLoginResult() != null;
    }

    @Override
    public Credentials getCredentials() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getSecurityPassword() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
