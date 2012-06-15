package com.redhat.solenopsis.credentials.impl;

import com.redhat.solenopsis.credentials.Credentials;
import com.redhat.solenopsis.credentials.CredentialsUtil;

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
