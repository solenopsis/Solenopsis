package com.redhat.solenopsis.ws.impl;

import com.redhat.solenopsis.ws.*;
import com.redhat.solenopsis.ws.impl.ServiceEnum;
import com.redhat.solenopsis.ws.impl.AbstractLoginSvc;
import com.redhat.sforce.soap.enterprise.*;
import java.net.URL;
import java.util.logging.Level;
import javax.xml.ws.BindingProvider;

/**
 *
 * Manages 
 *
 * @author sfloess
 *
 */
public final class DefaultEnterpriseSvc extends AbstractLoginSvc {   
    private LoginResult loginResult;
    
    private BindingProvider bindingProvider;
    
    protected LoginResult getLoginResult() {
        return loginResult;
    }
    
    protected BindingProvider getBindingProvider() {
        return bindingProvider;
    }
    
    public DefaultEnterpriseSvc(final Credentials credentials) {
        super(credentials, ServiceEnum.ENTERPRISE_SERVICE.getUrlSuffix());
        
        // Mostly meaningless - but prevents NPEs if login is not called.
        loginResult = new LoginResult();
    }
    
    /**
     * Login using the enterprise wsdl endpoint.
     * 
     * @throws Exception if there is any problem logging in.
     */
    @Override
    public void login() throws Exception {
        final URL wsdlUrl = DefaultEnterpriseSvc.class.getResource(ServiceEnum.ENTERPRISE_SERVICE.getWsdlResource());
        if (wsdlUrl == null) {
            throw new IllegalArgumentException("Could not find Polling API WSDL at "+ ServiceEnum.ENTERPRISE_SERVICE.getWsdlResource());
        }      
        
        SforceService service = new SforceService(wsdlUrl, ServiceEnum.ENTERPRISE_SERVICE.getQName());
        
        final Soap soap = service.getSoap();
        
        bindingProvider = (BindingProvider) soap;
        
        setUrl((BindingProvider) soap, getSvcUrl());     
            
        loginResult = soap.login(getCredentials().getUserName(), getSecurityPassword());        
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
