package org.solenopsis.ws.security;

import org.solenopsis.sforce.soap.enterprise.LoginResult;
import org.solenopsis.sforce.soap.enterprise.SforceService;
import org.solenopsis.sforce.soap.enterprise.Soap;
import org.solenopsis.credentials.Credentials;
import org.solenopsis.ws.SecurityWebSvc;
import org.solenopsis.ws.StandardWebServiceWsdlEnum;
import org.solenopsis.ws.WebServiceTypeEnum;
import org.solenopsis.ws.util.WebServiceUtil;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.ws.BindingProvider;

/**
 *
 * Manages 
 *
 * @author sfloess
 *
 */
public final class EnterpriseSecurityWebSvc implements SecurityWebSvc {   
    /**
     * Our logger.
     */
    private final Logger logger;
    
    private final SforceService service;
    
    private final Credentials credentials;
    
    private LoginResult loginResult;
    
    /**
     * Return the logger.
     */
    protected final Logger getLogger() {
        return logger;
    }       
    
    protected SforceService getService() {
        return service;
    }
    
    protected LoginResult getLoginResult() {
        if (loginResult != null) {
            return loginResult;
        }
        
        throw new IllegalStateException("Please login!");
    }
    
    protected Soap createPort() {                
        return getService().getSoap();
    }
    
    public EnterpriseSecurityWebSvc(final Credentials credentials) throws Exception {
        this.logger      = Logger.getLogger(getClass().getName());
        this.service     = new SforceService(StandardWebServiceWsdlEnum.ENTERPRISE_SERVICE.getWsdlResource(), StandardWebServiceWsdlEnum.ENTERPRISE_SERVICE.getQName());
        this.credentials = credentials;
    }
    
    @Override
    public Credentials getCredentials() {
        return credentials;
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
        return WebServiceUtil.computeServerUrl(getLoginResult().getServerUrl());
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
        
        final Soap port = createPort();
        
        // The URL here has to be test.salesforce.com or login.salesforce.com...
        WebServiceUtil.setUrl((BindingProvider) port, getCredentials().getUrl() + "/" + WebServiceTypeEnum.ENTERPRISE_SERVICE.getUrlSuffix() + "/" + getCredentials().getApiVersion());

        loginResult = port.login(getCredentials().getUserName(), getCredentials().getSecurityPassword());
    }

    @Override
    public void logout() throws Exception {
        if (getLogger().isLoggable(Level.INFO)) {
            getLogger().log(Level.INFO, "Logging out for:  User [{0}] Password [{1}]", new Object[]{getCredentials().getUserName(), getCredentials().getSecurityPassword()});
        }
        
        final Soap port = createPort();
          
        // The URL to logout has to be the server URL from login...        
        WebServiceUtil.setUrl((BindingProvider) port, getServerUrl() + "/" + WebServiceTypeEnum.ENTERPRISE_SERVICE.getUrlSuffix() + "/" + getCredentials().getApiVersion());
        WebServiceUtil.setSessionId((BindingProvider) port, getSessionId());
        
        port.logout();
        
        loginResult = null;
    }
    
    @Override
    public boolean isLoggedIn() {
        return loginResult != null;
    }
}
