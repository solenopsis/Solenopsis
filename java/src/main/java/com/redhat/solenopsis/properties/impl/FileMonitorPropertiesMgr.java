package com.redhat.solenopsis.properties.impl;

import com.redhat.solenopsis.util.FileMonitor;
import java.io.File;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;

/**
 *
 * Monitor a properties file.  If the file changes, reload the properties.
 *
 * @author sfloess
 *
 */
public class FileMonitorPropertiesMgr extends AbstractPropertiesMgr {     
    private FileMonitor fileMonitor;
    
    protected FileMonitor getFileMonitor() {
        return fileMonitor;
    }
    
    @Override
    public Properties getProperties() {
        if (getFileMonitor().isChanged()) {
            try {
                FilePropertiesMgr fpm = new FilePropertiesMgr(getFileMonitor().getFile());
                return fpm.getProperties();
            }
            
            catch(final IOException ioException) {  
                getLogger().log(Level.SEVERE, "Trouble loading propeties for file [" + getFileMonitor().getFile() + "] - returning empty properties!", ioException);
            }
        }
        
        return new Properties();
    }
    
    public FileMonitorPropertiesMgr(final File file) {
        this.fileMonitor = new FileMonitor(file);
    }
    
    public FileMonitorPropertiesMgr(final String fileName) {
        this.fileMonitor = new FileMonitor(fileName);
    }
}
