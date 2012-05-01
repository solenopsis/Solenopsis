package com.redhat.solenopsis.ws.properties;

import com.redhat.sforce.soap.metadata.FileProperties;
import java.util.List;
import java.util.Properties;

/**
 *
 * API to create properties from FileProperties
 *
 * @author sfloess
 *
 */
public interface PropertiesFactory {
    /**
     * Create a properties object from an instance of FileProperties.
     * 
     * @param filePropertiesList will be used to seed the return value.
     * 
     * @return a properties representation of <code>filePropertiesList</code>.
     */
    public Properties createProperties(List<FileProperties> filePropertiesList);
}
