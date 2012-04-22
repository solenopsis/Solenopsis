package com.redhat.solenopsis.ws.impl;

import com.redhat.sforce.soap.metadata.MetadataPortType;
import com.redhat.sforce.soap.metadata.MetadataService;
import com.redhat.solenopsis.ws.LoginWebSvc;
import com.redhat.solenopsis.ws.MetadataWebSvc;
import com.redhat.solenopsis.ws.WebServiceTypeEnum;

/**
 *
 * Manages 
 *
 * @author sfloess
 *
 */
public final class DefaultMetadataWebSvc extends AbstractSessionIdBasedWebSvc<MetadataPortType> implements MetadataWebSvc {
    private final MetadataService service;
    
    protected MetadataService getService() {
        return service;
    }
    
    @Override
    protected final String getServiceName() {
        return getLoginSvc().getCredentials().getApiVersion();
    }
    
    public DefaultMetadataWebSvc(final LoginWebSvc loginSvc) throws Exception {
        super(WebServiceTypeEnum.METADATA_SERVICE, loginSvc);
        
        service = new MetadataService(WebServiceEnum.METADATA_SERVICE.getWsdlResource(), WebServiceEnum.METADATA_SERVICE.getQName());
    }
    
    @Override
    protected  MetadataPortType createPort() {
        return getService().getMetadata();
    }
}
