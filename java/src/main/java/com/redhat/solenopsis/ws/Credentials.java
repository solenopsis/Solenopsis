package com.redhat.solenopsis.ws;

import java.util.Properties;

/**
 *
 * Used to hold credentials.
 *
 * @author sfloess
 *
 */
public class Credentials {
    private final String url;
    private final String userName;
    private final String password;
    private final String token;
    private final String apiVersion;
    
    public Credentials(final String url, final String userName, final String password, final String token, final String apiVersion) {
        this.url        = url;
        this.userName   = userName;
        this.password   = password;
        this.token      = token;
        this.apiVersion = apiVersion;
    }
    
    // TO DO - define an enum for these properties!!!
    public Credentials(final Properties properties) {
        this((String) properties.get("url"), (String) properties.get("username"), (String) properties.get("password"), (String) properties.get("token"), (String) properties.get("apiVersion"));
    }
    
    public String getUrl() {
        return url;
    }
    
    public String getUserName() {
        return userName;
    }
    
    public String getPassword() {
        return password;
    }
    
    public String getToken() {
        return token;
    }
    
    public String getApiVersion() {
        return apiVersion;
    }
}
