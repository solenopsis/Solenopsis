package org.solenopsis.ws.standard;

import org.solenopsis.sforce.soap.metadata.MetadataPortType;
import org.solenopsis.sforce.soap.metadata.MetadataService;
import org.solenopsis.ws.AbstractWebSvc;
import org.solenopsis.ws.SecurityWebSvc;
import org.solenopsis.ws.StandardWebServiceWsdlEnum;
import org.solenopsis.ws.WebServiceTypeEnum;

/**
 *
 * Manages 
 *
 * @author sfloess
 *
 */
public final class MetadataWebSvc extends AbstractWebSvc<MetadataService, MetadataPortType> {   
    public MetadataWebSvc(final SecurityWebSvc securitySvc) throws Exception {
        super (
            new MetadataService(StandardWebServiceWsdlEnum.METADATA_SERVICE.getWsdlResource(), StandardWebServiceWsdlEnum.METADATA_SERVICE.getQName()),
            securitySvc.getCredentials().getApiVersion(),
            WebServiceTypeEnum.METADATA_SERVICE,
            securitySvc
        );
    }
    
    @Override
    protected  MetadataPortType createPort() {
        return getService().getMetadata();
    }
}
