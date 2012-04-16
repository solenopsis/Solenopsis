package com.redhat.solenopsis.credentials.impl;

import com.redhat.solenopsis.credentials.Credentials;
import com.redhat.solenopsis.credentials.CredentialsUtil;
import java.util.Properties;

/**
 *
 * Uses properties to populate the credentials.
 *
 * @author sfloess
 *
 */
abstract class AbstractPropertiesCredentials implements Credentials {
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
    
    protected abstract Properties getProperties();
    
    protected AbstractPropertiesCredentials() {        
    }
    
    @Override
    public String getUrl() {
        return getProperties().getProperty(PropertyNameEnum.URL.getName());
    }
    
    @Override
    public String getUserName() {
        return getProperties().getProperty(PropertyNameEnum.USER_NAME.getName());
    }
    
    @Override
    public String getPassword() {
        return getProperties().getProperty(PropertyNameEnum.PASSWORD.getName());
    }
    
    @Override
    public String getToken() {
        return getProperties().getProperty(PropertyNameEnum.TOKEN.getName());
    }
    
    @Override
    public String getSecurityPassword() {
        return CredentialsUtil.computeSecurityPassword(getPassword(), getToken());
    }
    
    @Override
    public String getApiVersion() {
        return getProperties().getProperty(PropertyNameEnum.API_VERSION.getName());
    }    
}
