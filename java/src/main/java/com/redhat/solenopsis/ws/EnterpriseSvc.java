package com.redhat.solenopsis.ws;

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
public class EnterpriseSvc extends AbstractLoginBasedSvc {   
    private LoginResult loginResult;
    
    protected LoginResult getLoginResult() {
        return loginResult;
    }
    
    public EnterpriseSvc(final Credentials credentials) {
        super(credentials, ServiceEnum.ENTERPRISE_SERVICE.getUrlSuffix());
    }
    
    /**
     * Login using the enterprise wsdl endpoint.
     * 
     * @param userName the user name.
     * @param password the password.
     * @param token the security token.
     * @param url the URL of the enterprise wsdl endpoint.
     * 
     * @return the login result.  Will contain the session id.
     * 
     * @throws IllegalArgumentException if the enterprise wsdl is not found in the classpath.
     */
    @Override
    public void login() throws InvalidIdFault_Exception, LoginFault_Exception, UnexpectedErrorFault_Exception {
        final URL wsdlUrl = EnterpriseSvc.class.getResource(ServiceEnum.ENTERPRISE_SERVICE.getWsdlResource());
        if (wsdlUrl == null) {
            throw new IllegalArgumentException("Could not find Polling API WSDL at "+ ServiceEnum.ENTERPRISE_SERVICE.getWsdlResource());
        }      
        
        SforceService service = new SforceService(wsdlUrl, ServiceEnum.ENTERPRISE_SERVICE.getQName());
        
        final Soap soap = service.getSoap();
        
        setUrl((BindingProvider) soap, getSvcUrl());
                
        if (getLogger().isLoggable(Level.INFO)) {
            getLogger().log(Level.INFO, "Using URL [{0}]", getSvcUrl());
        }        
            
        loginResult = soap.login(getCredentials().getUserName(), getSecurityPassword());        
    }  
}
