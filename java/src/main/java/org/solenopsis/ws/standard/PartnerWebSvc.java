package com.redhat.solenopsis.ws.standard;

import com.redhat.sforce.soap.partner.SforceService;
import com.redhat.sforce.soap.partner.Soap;
import com.redhat.solenopsis.ws.AbstractWebSvc;
import com.redhat.solenopsis.ws.SecurityWebSvc;
import com.redhat.solenopsis.ws.StandardWebServiceWsdlEnum;
import com.redhat.solenopsis.ws.WebServiceTypeEnum;

/**
 *
 * Manages the Partner WSDL
 *
 * @author sfloess
 *
 */
public final class PartnerWebSvc extends AbstractWebSvc<SforceService, Soap> {
    public PartnerWebSvc(final SecurityWebSvc securitySvc) throws Exception {
        super (
            new SforceService(StandardWebServiceWsdlEnum.PARTNER_SERVICE.getWsdlResource(), StandardWebServiceWsdlEnum.PARTNER_SERVICE.getQName()),
            securitySvc.getCredentials().getApiVersion(),
            WebServiceTypeEnum.PARTNER_SERVICE,
            securitySvc
        );
    }
    
    @Override
    protected  Soap createPort() {
        return getService().getSoap();
    }
}
