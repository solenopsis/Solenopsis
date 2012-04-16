package com.redhat.solenopsis.credentials.impl;

import java.util.Properties;

/**
 *
 * Uses properties to populate the credentials.
 *
 * @author sfloess
 *
 */
public class PropertiesCredentials extends AbstractPropertiesCredentials {
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
    
    private final Properties properties;
    
    protected Properties getProperties() {
        return properties;
    }
    
    public PropertiesCredentials(final Properties properties) {
        this.properties = properties;
    }
}
