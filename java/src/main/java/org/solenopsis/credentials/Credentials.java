package com.redhat.solenopsis.credentials;

/**
 *
 * Defines credentials.
 *
 * @author sfloess
 *
 */
public interface Credentials {    
    public String getUrl();
    
    public String getUserName();
    
    public String getPassword();
    
    public String getToken();
    
    public String getSecurityPassword();
    
    public String getApiVersion();
}
