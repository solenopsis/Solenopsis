package com.redhat.solenopsis.ws;

import com.redhat.sforce.soap.partner.*;
import java.net.URL;
import javax.xml.ws.BindingProvider;

/**
 *
 * Manages the Partner WSDL
 *
 * @author sfloess
 *
 */
public class PartnerSvc extends AbstractLoginBasedSvc {
    
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
    public LoginResult login(final String userName, final String password, final String token, final String url) throws InvalidIdFault_Exception, LoginFault_Exception, UnexpectedErrorFault_Exception {
        final URL wsdlUrl = PartnerSvc.class.getResource(ServiceEnum.PARTNER_SERVICE.getWsdlResource());
        if (wsdlUrl == null) {
            throw new IllegalArgumentException("Could not find Polling API WSDL at "+ ServiceEnum.ENTERPRISE_SERVICE.getWsdlResource());
        }      
        
        SforceService service = new SforceService(wsdlUrl, ServiceEnum.PARTNER_SERVICE.getQName());
        
        Soap soap = service.getSoap();
        
        setUrl((BindingProvider) soap, url);
                
        return soap.login(userName, computeWebServicePassword(password, token));        
    }  
}
