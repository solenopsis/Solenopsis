package com.redhat.solenopsis.ws.impl;

import com.redhat.sforce.soap.metadata.MetadataPortType;
import com.redhat.sforce.soap.metadata.MetadataService;
import com.redhat.solenopsis.ws.LoginSvc;
import com.redhat.solenopsis.ws.MetadataSvc;

/**
 *
 * Manages 
 *
 * @author sfloess
 *
 */
public class DefaultMetadataSvc extends AbstractSessionIdBasedSvc<MetadataPortType> implements MetadataSvc {
    private final MetadataService service;
    
    protected MetadataService getService() {
        return service;
    }
            
    @Override
    protected String getServiceUrl() {
        return getLoginSvc().getMetadataServerUrl();
    }
    
    public DefaultMetadataSvc(final LoginSvc loginSvc) throws Exception {
        super(loginSvc);
        
        service = new MetadataService(ServiceEnum.PARTNER_SERVICE.getWsdlResource(), ServiceEnum.PARTNER_SERVICE.getQName());
    }
    
    @Override
    protected  MetadataPortType createPort() {
        return getService().getMetadata();
    }
}
