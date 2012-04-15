package com.redhat.solenopsis.ws.impl;

import com.redhat.sforce.soap.metadata.MetadataPortType;
import com.redhat.sforce.soap.metadata.MetadataService;
import com.redhat.solenopsis.ws.LoginSvc;
import com.redhat.solenopsis.ws.MetadataSvc;
import com.redhat.solenopsis.ws.ServiceTypeEnum;

/**
 *
 * Manages 
 *
 * @author sfloess
 *
 */
public final class DefaultMetadataSvc extends AbstractSessionIdBasedSvc<MetadataPortType> implements MetadataSvc {
    private final MetadataService service;
    
    protected MetadataService getService() {
        return service;
    }
    
    @Override
    protected final String getServiceName() {
        return getLoginSvc().getCredentials().getApiVersion();
    }
    
    public DefaultMetadataSvc(final LoginSvc loginSvc) throws Exception {
        super(ServiceTypeEnum.METADATA_SERVICE, loginSvc);
        
        service = new MetadataService(ServiceEnum.METADATA_SERVICE.getWsdlResource(), ServiceEnum.METADATA_SERVICE.getQName());
    }
    
    @Override
    protected  MetadataPortType createPort() {
        return getService().getMetadata();
    }
}
