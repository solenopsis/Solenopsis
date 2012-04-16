package com.redhat.solenopsis.util;

import com.redhat.solenopsis.credentials.Credentials;
import com.redhat.solenopsis.credentials.impl.DefaultCredentials;
import com.redhat.solenopsis.credentials.impl.PropertiesCredentials;
import java.io.File;
import java.io.FileInputStream;
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
public class MonitoredPropertiesFile { 
    /**
     * Our logger.
     */
    private static final Logger logger = Logger.getLogger(MonitoredPropertiesFile.class.getName());
            
    private File file;
    
    private FileMonitor fileMonitor;
    
    private Properties properties;
    
    protected Logger getLogger() {
        return logger;
    }
    
    protected File getFile() {
        return file;
    }
    
    protected FileMonitor getFileMonitor() {
        return fileMonitor;
    }
    
    public Properties getProperties() {
        if (getFileMonitor().isChanged()) {                    
            FileInputStream fis = null;
            
            try {
                fis = new FileInputStream(getFile());
                
                final Properties props = new Properties();
                props.load(fis);
        
                properties = props;
            }
            
            catch(final Exception exception) {
                getLogger().log(Level.WARNING, "Trouble reading properties for file " + getFile(), exception);
            }
            
            finally {
                if (fis != null) {
                    try {
                        fis.close();
                    }
                    
                    catch (final IOException ioException) {
                        getLogger().log(Level.WARNING, "Trouble closing file input stream for file " + getFile(), ioException);
                    }
                }
            }
        }
        
        return properties;
    }
    
    public MonitoredPropertiesFile(final File file) {
        this.file        = file;
        this.fileMonitor = new FileMonitor(file);
        this.properties  = new Properties();
    }
    
    public MonitoredPropertiesFile(final String fileName) {
        this(new File(fileName));
    }
}
