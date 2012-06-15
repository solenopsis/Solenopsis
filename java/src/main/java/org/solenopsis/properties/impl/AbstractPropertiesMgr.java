package com.redhat.solenopsis.properties.impl;

import com.redhat.solenopsis.properties.PropertiesMgr;
import java.util.logging.Logger;

/**
 *
 * Abstract base class for property management.
 *
 * @author sfloess
 *
 */
public abstract class AbstractPropertiesMgr implements PropertiesMgr { 
    /**
     * Our logger.
     */
    private final Logger logger;    
    
    /**
     * Return the logger.
     */
    protected Logger getLogger() {
        return logger;        
    }
    
    public AbstractPropertiesMgr() {
        logger = Logger.getLogger(getClass().getName());
    }
}
