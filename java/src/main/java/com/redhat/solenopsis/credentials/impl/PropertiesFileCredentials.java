package com.redhat.solenopsis.credentials.impl;

import com.redhat.solenopsis.properties.PropertiesMgr;
import com.redhat.solenopsis.properties.impl.FilePropertiesMgr;
import java.io.File;
import java.io.IOException;
import java.util.Properties;

/**
 *
 * Monitors a credentials property file and loads when it changes.
 *
 * @author sfloess
 *
 */
public class PropertiesFileCredentials extends AbstractPropertiesCredentials { 
    
    private final PropertiesMgr filePropertiesMgr;
    
    protected PropertiesMgr getPropertiesFileMonitor() {
        return filePropertiesMgr;
    }
    
    @Override
    protected Properties getProperties() {
        return getPropertiesFileMonitor().getProperties();
    }
    
    public PropertiesFileCredentials(final File credentialsFile) throws IOException {
        this.filePropertiesMgr = new FilePropertiesMgr(credentialsFile);
    }
    
    public PropertiesFileCredentials(final String fileName) throws IOException {
        this(new File(fileName));
    }
}
