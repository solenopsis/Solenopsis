package org.solenopsis.properties.impl;

import org.solenopsis.util.FileMonitor;
import java.io.File;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * Monitor a properties file.  If the file changes, reload the properties.
 *
 * @author sfloess
 *
 */
public class FileMonitorPropertiesMgr extends AbstractPropertiesMgr {     
    private final FileMonitor fileMonitor;
    
    private Properties properties;
    
    protected FileMonitor getFileMonitor() {
        return fileMonitor;
    }
    
    protected static Properties loadProperties(final Logger logger, final FileMonitor fileMonitor) {
        try {
            final FilePropertiesMgr fpm = new FilePropertiesMgr(fileMonitor.getFile());
            return fpm.getProperties();      
        }
            
        catch(final IOException ioException) {  
            logger.log(Level.SEVERE, "Trouble loading propeties for file [" + fileMonitor.getFile() + "] - returning empty properties!", ioException);
        }
        
        return new Properties();
    }
    
    @Override
    public Properties getProperties() {
        if (getFileMonitor().isChanged()) {
            properties = loadProperties(getLogger(), getFileMonitor());
        }
        
        return properties;
    }
    
    public FileMonitorPropertiesMgr(final File file) {
        this.fileMonitor = new FileMonitor(file);
        this.properties  = loadProperties(getLogger(), fileMonitor);
    }
    
    public FileMonitorPropertiesMgr(final String fileName) {
        this.fileMonitor = new FileMonitor(fileName);
    }
}
