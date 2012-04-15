package com.redhat.solenopsis.ws;

import com.redhat.solenopsis.ws.util.CredentialsUtil;
import java.util.Properties;

/**
 *
 * Used to hold credentials.
 *
 * @author sfloess
 *
 */
public class Credentials {
    public enum PropertyNameEnum {
        URL("url"),
        USER_NAME("username"),
        PASSWORD("password"),
        TOKEN("token"),
        API_VERSION("apiVersion");
        
        private final String name;
        
        private PropertyNameEnum(final String name) {
            this.name = name;
        }
        
        public String getName() {
            return name;
        }
    };
    
    private final String url;
    private final String userName;
    private final String password;
    private final String token;
    private final String securityPassword;
    private final String apiVersion;
    
    public Credentials(final String url, final String userName, final String password, final String token, final String apiVersion) {
        this.url              = url;
        this.userName         = userName;
        this.password         = password;
        this.token            = token;
        this.securityPassword = CredentialsUtil.computeWebServicePassword(password, token);
        this.apiVersion       = apiVersion;
    }
    
    public Credentials(final Properties properties) {
        this(
            properties.getProperty(PropertyNameEnum.URL.getName()),
            properties.getProperty(PropertyNameEnum.USER_NAME.getName()),
            properties.getProperty(PropertyNameEnum.PASSWORD.getName()),
            properties.getProperty(PropertyNameEnum.TOKEN.getName()),
            properties.getProperty(PropertyNameEnum.API_VERSION.getName())
        );
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
    
    public String getSecurityPassword() {
        return securityPassword;
    }
    
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
