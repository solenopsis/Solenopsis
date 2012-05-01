package com.redhat.solenopsis.ws.properties.impl;

import com.redhat.sforce.soap.metadata.FileProperties;
import com.redhat.solenopsis.ws.properties.PropertiesFactory;
import java.util.List;
import java.util.Properties;

/**
 *
 * API to create properties from FileProperties
 *
 * @author sfloess
 *
 */
public class DefaultPropertiesFactory implements PropertiesFactory {
    /**
     * {@inheritDoc}
     */
    @Override
    public Properties createProperties(final List<FileProperties> filePropertiesList) {
        final Properties retVal = new Properties();
        
        final StringBuilder sb = new StringBuilder();

        String name = null;
        
        for (final FileProperties fileProperties : filePropertiesList) {
            sb.append(fileProperties.getFullName()).append(' ');
            
            name = fileProperties.getType();
        }
        
        if (null != name && ! "".equals (name)) {
            retVal.setProperty(name, sb.toString());
        }
        
        return retVal;
    }
}
