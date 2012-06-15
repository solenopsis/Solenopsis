package org.solenopsis.credentials.impl;

import org.solenopsis.credentials.Credentials;
import org.solenopsis.credentials.CredentialsUtil;

/**
 *
 * Uses properties to populate the credentials.
 *
 * @author sfloess
 *
 */
abstract class AbstractCredentials implements Credentials {        
    protected AbstractCredentials() {        
    }
    
    @Override
    public final String getSecurityPassword() {
        return CredentialsUtil.computeSecurityPassword(getPassword(), getToken());
    }  
}
