package com.redhat.solenopsis.ws.impl;

import com.redhat.sforce.soap.partner.LoginResult;
import com.redhat.sforce.soap.partner.SforceService;
import com.redhat.sforce.soap.partner.Soap;
import com.redhat.solenopsis.credentials.Credentials;
import com.redhat.solenopsis.ws.ServiceTypeEnum;
import java.util.logging.Level;

/**
 *
 * Manages the Partner WSDL
 *
 * @author sfloess
 *
 */
public final class DefaultPartnerSvc extends AbstractLoginSvc<Soap> {

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
    
    public DefaultPartnerSvc(final Credentials credentials) throws Exception { 
        super(ServiceTypeEnum.PARTNER_SERVICE, credentials);
        
        service = new SforceService(ServiceEnum.PARTNER_SERVICE.getWsdlResource(), ServiceEnum.PARTNER_SERVICE.getQName());
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
