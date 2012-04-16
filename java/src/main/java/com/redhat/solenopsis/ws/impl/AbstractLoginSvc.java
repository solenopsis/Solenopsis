package com.redhat.solenopsis.ws.impl;

import com.redhat.solenopsis.credentials.Credentials;
import com.redhat.solenopsis.ws.LoginSvc;
import com.redhat.solenopsis.ws.ServiceTypeEnum;
import com.redhat.solenopsis.credentials.CredentialsUtil;
import java.net.URL;
import java.util.logging.Level;
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
    public final String getUrl() {
        return getCredentials().getUrl();
    }
    
    @Override
    protected String getServiceName() {
        return getCredentials().getApiVersion();
    }    
    
    protected final String computeServerUrl(final String rawUrl) {
        try {
            final URL url = new URL(rawUrl);

            return url.getProtocol() + "://" + url.getHost();
        }
        
        catch (final Exception exception) {
            getLogger().log(Level.SEVERE, "Trouble getting protocol and host!", exception);
        }
        
        return "";
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
