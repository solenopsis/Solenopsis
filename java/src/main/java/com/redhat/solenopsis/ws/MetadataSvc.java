package com.redhat.solenopsis.ws;

import com.redhat.sforce.soap.enterprise.LoginResult;
import com.redhat.sforce.soap.metadata.MetadataPortType;
import com.redhat.sforce.soap.metadata.MetadataService;
import java.net.URL;
import javax.xml.ws.BindingProvider;

/**
 *
 * Manages 
 *
 * @author sfloess
 *
 */
public class MetadataSvc extends AbstractSessionIdBasedSvc {
    
    /**
     * Return the port.
     */
    public MetadataPortType getPort(final String sessionId, final String url) throws Exception {
        final URL wsdlUrl = MetadataSvc.class.getResource(ServiceEnum.METADATA_SERVICE.getWsdlResource());
        if (wsdlUrl == null) {
            throw new IllegalArgumentException("Could not find WSDL at "+ ServiceEnum.METADATA_SERVICE.getWsdlResource());
        }
        
        MetadataService service = new MetadataService(wsdlUrl, ServiceEnum.METADATA_SERVICE.getQName());    
        MetadataPortType port = service.getMetadata();

        setSessionId((BindingProvider) port, sessionId);
        setUrl((BindingProvider) port, url);
        
        return port;   
    }        
    
    /**
     * Return the port.
     */
    public MetadataPortType getPort(final com.redhat.sforce.soap.partner.LoginResult loginResult) throws Exception {        
        final String url = loginResult.getMetadataServerUrl();
        
        return getPort(loginResult.getSessionId(), url.substring(0, url.lastIndexOf('/')));   
    }
    
    /**
     * Return the port.
     */
    public MetadataPortType getPort(final LoginResult loginResult) throws Exception {        
        final String url = loginResult.getMetadataServerUrl();
        
        return getPort(loginResult.getSessionId(), url.substring(0, url.lastIndexOf('/')));   
    }     
}
