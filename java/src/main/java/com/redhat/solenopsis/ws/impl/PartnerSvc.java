package com.redhat.solenopsis.ws.impl;

import com.redhat.solenopsis.ws.impl.ServiceEnum;
import com.redhat.solenopsis.ws.impl.AbstractLoginSvc;
import com.redhat.sforce.soap.partner.*;
import com.redhat.solenopsis.ws.Credentials;
import java.net.URL;
import javax.xml.ws.BindingProvider;

/**
 *
 * Manages the Partner WSDL
 *
 * @author sfloess
 *
 */
public class PartnerSvc extends AbstractLoginSvc {
    private LoginResult loginResult;
    
    protected LoginResult getLoginResult() {
        return loginResult;
    }
    
    public PartnerSvc(final Credentials credentials) {
        super(credentials, ServiceEnum.PARTNER_SERVICE.getUrlSuffix());
        
        // Mostly meaningless - but prevents NPEs if login is not called.
        loginResult = new LoginResult();
    }
    
    /**
     * Login using the partner wsdl endpoint.
     * 
     * @throws Exception if there is a problem logging in.
     */
    @Override
    public void login() throws Exception {
        final URL wsdlUrl = PartnerSvc.class.getResource(ServiceEnum.PARTNER_SERVICE.getWsdlResource());
        if (wsdlUrl == null) {
            throw new IllegalArgumentException("Could not find Polling API WSDL at "+ ServiceEnum.ENTERPRISE_SERVICE.getWsdlResource());
        }      
        
        SforceService service = new SforceService(wsdlUrl, ServiceEnum.PARTNER_SERVICE.getQName());
        
        Soap soap = service.getSoap();
        
        setUrl((BindingProvider) soap, getSvcUrl());
                
        soap.login(getCredentials().getUserName(), getSecurityPassword());        
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
