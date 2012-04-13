package com.redhat.solenopsis.ws.impl;

import com.redhat.sforce.soap.enterprise.LoginResult;
import com.redhat.sforce.soap.metadata.MetadataPortType;
import com.redhat.sforce.soap.metadata.MetadataService;
import com.redhat.solenopsis.ws.LoginSvc;
import com.redhat.solenopsis.ws.MetadataSvc;
import java.net.URL;
import javax.xml.ws.BindingProvider;

/**
 *
 * Manages 
 *
 * @author sfloess
 *
 */
public class DefaultMetadataSvc extends AbstractSessionIdBasedSvc implements MetadataSvc {

    public DefaultMetadataSvc(final LoginSvc loginSvc) {
        super(loginSvc);
    }
    
    /**
     * Return the port.
     */
    public MetadataPortType getPort(final String sessionId, final String url) throws Exception {
        final URL wsdlUrl = DefaultMetadataSvc.class.getResource(ServiceEnum.METADATA_SERVICE.getWsdlResource());
        if (wsdlUrl == null) {
            throw new IllegalArgumentException("Could not find WSDL at "+ ServiceEnum.METADATA_SERVICE.getWsdlResource());
        }
        
        MetadataService service = new MetadataService(wsdlUrl, ServiceEnum.METADATA_SERVICE.getQName());    
        MetadataPortType port = service.getMetadata();

        setSessionId((BindingProvider) port, sessionId);
        setUrl((BindingProvider) port, url);
        
        return port;   
    }        

    @Override
    public MetadataPortType getPort() throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void login() throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
