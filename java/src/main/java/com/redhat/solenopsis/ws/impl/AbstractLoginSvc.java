package com.redhat.solenopsis.ws.impl;

import com.redhat.solenopsis.ws.*;
import com.redhat.solenopsis.ws.util.CredentialsUtil;
import javax.xml.ws.BindingProvider;

/**
 *
 * Denotes a service that can be used to login to SFDC.  Defined in a neutral
 * way considering both an enterprise.wsdl and partner.wsdl.
 *
 * @author sfloess
 *
 */
public abstract class AbstractLoginSvc<P> extends AbstractSvc<P> implements LoginSvc<P> {
    private final Credentials credentials;
    
    private P port;

    protected AbstractLoginSvc(final ServiceTypeEnum serviceType, final Credentials credentials) {
        super(serviceType);
        
        this.credentials = credentials;
    }
           
    /**
     * Return the credentials used for login.
     * 
     * @return the credentials.
     */
    @Override
    public final Credentials getCredentials() {
        return credentials;
    }
    
    @Override
    public final String getSecurityPassword() {
        return CredentialsUtil.computeWebServicePassword(getCredentials());
    } 
    
    @Override
    public final String getUrl() {
        return getCredentials().getUrl();
    }
    
    @Override
    protected String getServiceName() {
        return getCredentials().getApiVersion();
    }    
    
    @Override
    public final P getPort() throws Exception {
        if (null == port) {
            port = createPort();
            
            setUrl((BindingProvider) port, getUrl() + "/" + getServiceType().getUrlSuffix() + "/" + getServiceName());
        }
        
        return port;
    }
}
