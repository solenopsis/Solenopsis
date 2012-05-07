package com.redhat.solenopsis.ws;

import com.redhat.solenopsis.credentials.Credentials;

/**
 *
 * Denotes a service that can be used to login to SFDC.  Defined in a neutral
 * way considering both an enterprise.wsdl and partner.wsdl.
 *
 * @author sfloess
 *
 */
public interface SecurityWebSvc {
    /**
     * Return the credentials used for login.
     * 
     * @return the credentials.
     */
    public Credentials getCredentials();
    
    /**
     * Request a new login.
     * 
     * Returns the URL of the service.
     * 
     * @throws Exception if any problems arise logging in.
     */
    public void login() throws Exception;
    
    /**
     * Force a logout.
     * 
     * @throws Exception if any problems arise logging out. 
     */
    public void logout() throws Exception;

    public boolean isLoggedIn();        

    public String getMetadataServerUrl();

    public boolean isPasswordExpired();

    public boolean isSandbox();

    public String getServerUrl() ;

    public String getSessionId();

    public String getUserId(); 

}
