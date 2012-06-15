package com.redhat.solenopsis.ws.standard;

import com.redhat.sforce.soap.enterprise.SforceService;
import com.redhat.sforce.soap.enterprise.Soap;
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
public final class EnterpriseWebSvc extends AbstractWebSvc<SforceService, Soap> {  
    public EnterpriseWebSvc(final SecurityWebSvc securitySvc) throws Exception {
        super (
            new SforceService(StandardWebServiceWsdlEnum.ENTERPRISE_SERVICE.getWsdlResource(), StandardWebServiceWsdlEnum.ENTERPRISE_SERVICE.getQName()),
            securitySvc.getCredentials().getApiVersion(),
            WebServiceTypeEnum.ENTERPRISE_SERVICE,
            securitySvc
        );
    }
    
    @Override
    protected  Soap createPort() {
        return getService().getSoap();
    }
}
