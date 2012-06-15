package org.solenopsis.ws.standard;

import org.solenopsis.sforce.soap.enterprise.SforceService;
import org.solenopsis.sforce.soap.enterprise.Soap;
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
