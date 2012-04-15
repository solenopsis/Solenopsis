package com.redhat.solenopsis.ws.impl;

import com.redhat.sforce.soap.partner.LoginResult;
import com.redhat.sforce.soap.partner.SforceService;
import com.redhat.sforce.soap.partner.Soap;
import com.redhat.solenopsis.ws.Credentials;
import com.redhat.solenopsis.ws.LoginSvc;

/**
 *
 * Manages the Partner WSDL
 *
 * @author sfloess
 *
 */
public class DefaultPartnerSvc extends AbstractSvc<Soap> implements LoginSvc<Soap> {
    private LoginResult loginResult;

    private final SforceService service;
    
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
    
    protected SforceService getService() {
        return service;
    }
    
    @Override
    protected Soap createPort() {                
        return getService().getSoap();
    }
    
    public DefaultPartnerSvc(final Credentials credentials) throws Exception {    
        service = new SforceService(ServiceEnum.PARTNER_SERVICE.getWsdlResource(), ServiceEnum.PARTNER_SERVICE.getQName());
    }
    
    
    @Override
    public void login() throws Exception {
        loginResult = null;
        loginResult = createPort().login(getCredentials().getUserName(), getSecurityPassword());
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

}
