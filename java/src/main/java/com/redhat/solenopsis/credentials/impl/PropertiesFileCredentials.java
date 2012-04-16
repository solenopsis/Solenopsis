package com.redhat.solenopsis.credentials.impl;

import com.redhat.solenopsis.credentials.Credentials;
import com.redhat.solenopsis.util.FileMonitor;
import com.redhat.solenopsis.util.PropertiesFileMonitor;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * Monitors a credentials property file.
 *
 * @author sfloess
 *
 */
public class PropertiesFileCredentials extends AbstractPropertiesCredentials { 
    /**
     * Our logger.
     */
    private static final Logger logger = Logger.getLogger(PropertiesFileCredentials.class.getName());
    
    private final PropertiesFileMonitor propertiesFileMonitor;
    
    protected Logger getLogger() {
        return logger;
    }
    
    protected PropertiesFileMonitor getPropertiesFileMonitor() {
        return propertiesFileMonitor;
    }
    
    @Override
    protected Properties getProperties() {
        return getPropertiesFileMonitor().getProperties();
    }
    
    public PropertiesFileCredentials(final File credentialsFile) {
        this.propertiesFileMonitor = new PropertiesFileMonitor(credentialsFile);
    }
    
    public PropertiesFileCredentials(final String fileName) {
        this(new File(fileName));
    }
}
