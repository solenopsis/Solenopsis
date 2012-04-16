package com.redhat.solenopsis.credentials.impl;

/**
 *
 * Default implementation of Credentials.
 *
 * @author sfloess
 *
 */
public class DefaultCredentials extends AbstractCredentials {   
    private final String url;
    private final String userName;
    private final String password;
    private final String token;
    private final String apiVersion;
    
    public DefaultCredentials(final String url, final String userName, final String password, final String token, final String apiVersion) {
        this.url              = url;
        this.userName         = userName;
        this.password         = password;
        this.token            = token;        
        this.apiVersion       = apiVersion;
    }
    
    @Override
    public String getUrl() {
        return url;
    }
    
    @Override
    public String getUserName() {
        return userName;
    }
    
    @Override
    public String getPassword() {
        return password;
    }
    
    @Override
    public String getToken() {
        return token;
    }
    
    @Override
    public String getApiVersion() {
        return apiVersion;
    }
    
    /**
     * Using <code>sb</code>, compute the string representation of self whose
     * prefix for computation is <code>prefix</code>.
     * 
     * @param sb will have the string representation of self appended.
     * @param prefix is the prefix to be first appended prior to self's string representation.
     */
    public void toString(final StringBuilder sb, final String prefix) {
        sb.append(prefix).append("apiVersion       [").append(getApiVersion()).append("]\n");
        sb.append(prefix).append("password         [").append(getPassword()).append("]\n");
        sb.append(prefix).append("securityPassword [").append(getSecurityPassword()).append("]\n");
        sb.append(prefix).append("token            [").append(getToken()).append("]\n");
        sb.append(prefix).append("url              [").append(getUrl()).append("]\n");
        sb.append(prefix).append("userName         [").append(getUserName()).append("]");
    }
    
    /**
     * Using <code>sb</code>, compute the string representation of self.
     * 
     * @param sb will have the string representation of self appended.
     */
    public void toString(final StringBuilder sb) {
        toString(sb, "");
    }
    
    /**
     * Using <code>prefix</code> return the string representation of self.
     * 
     * @param prefix is the prefix to emit when return the string representation of self.
     * 
     * @return the string representation of self.
     */
    public String toString(final String prefix) {
        final StringBuilder sb = new StringBuilder();
        
        toString(sb, prefix);
        
        return sb.toString();
    }
    
    /**
     * Return the string representation of self.
     * 
     * @return the string representation of self.
     */
    @Override
    public String toString() {
        return toString("");
    }
}
