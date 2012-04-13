package com.redhat.solenopsis.ws;

/**
 *
 * Denotes a service that can be used to login to SFDC.  Defined in a neutral
 * way considering both an enterprise.wsdl and partner.wsdl.
 *
 * @author sfloess
 *
 */
public interface LoginSvc extends Svc {
    /**
     * Return the credentials used for login.
     * 
     * @return the credentials.
     */
    public Credentials getCredentials();
    
    public String getSecurityPassword();
    
    public String getSvcUrl();    

    public String getMetadataServerUrl();

    public boolean isPasswordExpired();

    public boolean isSandbox();

    public String getServerUrl() ;

    public String getSessionId();

    public String getUserId(); 

}
