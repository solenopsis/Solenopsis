package org.solenopsis.properties.impl;

import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;

/**
 *
 * Using an input stream as a start, will load properties from the stream.
 *
 * @author sfloess
 *
 */
public class InputStreamPropertiesMgr extends AbstractPropertiesMgr { 
    protected final Properties properties;
    
    @Override
    public Properties getProperties() {
        return properties;
    }
    
    public InputStreamPropertiesMgr(final InputStream inputStream) {
        properties = new Properties();
        
        try {
            properties.load(inputStream);
        }

        catch(final Exception exception) {
            getLogger().log(Level.WARNING, "Trouble reading input stream!", exception);
        }        
    }
}
