package com.redhat.solenopsis.ws.impl.bak;

import com.redhat.solenopsis.ws.*;
import com.redhat.solenopsis.ws.impl.bak.ServiceEnum;
import com.redhat.solenopsis.ws.impl.bak.AbstractLoginSvc;
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
    
    protected LoginResult getLoginResult() {
        return loginResult;
    }
    
    @Override
    protected BindingProvider createBindingProvider() {
        SforceService service = new SforceService(getWsdlUrl(), ServiceEnum.ENTERPRISE_SERVICE.getQName());
        
        final Soap soap = service.getSoap();
        
        return (BindingProvider) soap;
    }
    
    public DefaultEnterpriseSvc(final Credentials credentials) throws Exception {
        super(credentials, ServiceEnum.ENTERPRISE_SERVICE.getWsdlResource(), ServiceEnum.ENTERPRISE_SERVICE.getUrlSuffix());
        
        // Mostly meaningless - but prevents NPEs if login is not called.
        loginResult = new LoginResult();
    }
    
    @Override
    protected void doLogin() throws Exception {
        loginResult = ((Soap) getBindingProvider()).login(getCredentials().getUserName(), getSecurityPassword());
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
