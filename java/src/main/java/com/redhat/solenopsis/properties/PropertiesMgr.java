package com.redhat.solenopsis.properties;

import java.util.Properties;

/**
 *
 * Manages properties.
 *
 * @author sfloess
 *
 */
public interface PropertiesMgr { 
    /**
     * Return the properties managed.
     * 
     * @return the properties managed.
     */
    public Properties getProperties();
}
