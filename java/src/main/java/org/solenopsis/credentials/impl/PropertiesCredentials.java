package org.solenopsis.credentials.impl;

import org.solenopsis.properties.PropertiesMgr;
import java.util.Properties;

/**
 *
 * Uses properties to populate the credentials.
 *
 * @author sfloess
 *
 */
public class PropertiesCredentials extends AbstractCredentials {
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
    
    private final PropertiesMgr propertiesMgr;
    
    protected PropertiesMgr getPropertiesMgr() {
        return propertiesMgr;
    }
    
    protected Properties getProperties() {
        return getPropertiesMgr().getProperties();
    }
    
    public PropertiesCredentials(PropertiesMgr propertiesMgr) {
        this.propertiesMgr = propertiesMgr;
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
    public String getApiVersion() {
        return getProperties().getProperty(PropertyNameEnum.API_VERSION.getName());
    }    
}
