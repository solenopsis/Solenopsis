package com.redhat.solenopsis.ws.impl.bak;

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

    public DefaultMetadataSvc(final LoginSvc loginSvc, final double apiVersion) throws Exception {
        super(loginSvc);

        //, ServiceEnum.METADATA_SERVICE.getWsdlResource(), ServiceEnum.METADATA_SERVICE.getUrlSuffix() + "/" + loginSvc.getCredentials().getApiVersion());
    }
    
    @Override
    protected BindingProvider createBindingProvider() {
        MetadataService service = new MetadataService(getWsdlUrl(), ServiceEnum.METADATA_SERVICE.getQName());    
        MetadataPortType port = service.getMetadata();
        
        return (BindingProvider) port;
    }

    @Override
    public MetadataPortType getPort() throws Exception {
        return (MetadataPortType) getBindingProvider();
    }
}
