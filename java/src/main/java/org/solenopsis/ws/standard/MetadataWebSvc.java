package com.redhat.solenopsis.ws.standard;

import com.redhat.sforce.soap.metadata.MetadataPortType;
import com.redhat.sforce.soap.metadata.MetadataService;
import com.redhat.solenopsis.ws.AbstractWebSvc;
import com.redhat.solenopsis.ws.SecurityWebSvc;
import com.redhat.solenopsis.ws.StandardWebServiceWsdlEnum;
import com.redhat.solenopsis.ws.WebServiceTypeEnum;

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
