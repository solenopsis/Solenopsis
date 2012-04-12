package com.redhat.solenopsis.ws;

import com.redhat.sforce.soap.enterprise.*;
import java.net.URL;
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
public class Enterprise {
    /**
     * Our logger.
     */
    private static final Logger logger = Logger.getLogger(Enterprise.class.getName());
    
    /**
     * Return the logger.
     */
    protected static Logger getLogger() {
        return logger;
    }
    
    /**
     * Compute the web service password - this is a combination of <code>password</code>
     * plus <code>token</code>.
     * 
     * @param password the password.
     * @param token the security token.
     * 
     * @return the web service password (combination of password plus token).
     */
    public static String computeWebServicePassword(final String password, final String token) {                
        if (getLogger().isLoggable(Level.INFO)) {
            getLogger().log(Level.INFO, "Computed web service password [" + password + token + "]");
        }
        
        return password + token;
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
    public static LoginResult login(final String userName, final String password, final String token, final String url) throws InvalidIdFault_Exception, LoginFault_Exception, UnexpectedErrorFault_Exception {
        final URL wsdlUrl = Enterprise.class.getResource(ServiceEnum.ENTERPRISE_SERVICE.getWsdlResource());
        if (wsdlUrl == null) {
            throw new IllegalArgumentException("Could not find Polling API WSDL at "+ ServiceEnum.ENTERPRISE_SERVICE.getWsdlResource());
        }      
        
        SforceService service = new SforceService(wsdlUrl, ServiceEnum.ENTERPRISE_SERVICE.getQName());
        ((BindingProvider) service.getSoap()).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, url);
                
        if (getLogger().isLoggable(Level.INFO)) {
            getLogger().log(Level.INFO, "Using URL [{0}]", url);
        }        
            
        return service.getSoap().login(userName, computeWebServicePassword(password, token));        
    }  
}
