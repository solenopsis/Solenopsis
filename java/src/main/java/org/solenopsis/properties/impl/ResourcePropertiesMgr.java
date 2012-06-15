package org.solenopsis.properties.impl;

import java.io.IOException;

/**
 *
 * Loads properties as a resource.
 *
 * @author sfloess
 *
 */
public final class ResourcePropertiesMgr extends InputStreamPropertiesMgr { 
    public ResourcePropertiesMgr(final String resourceName) throws IOException {
        super(ResourcePropertiesMgr.class.getResourceAsStream(resourceName));     
    }
}
