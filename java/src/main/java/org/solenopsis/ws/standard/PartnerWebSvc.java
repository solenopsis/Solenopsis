package org.solenopsis.ws.standard;

import org.solenopsis.sforce.soap.partner.SforceService;
import org.solenopsis.sforce.soap.partner.Soap;
import org.solenopsis.ws.AbstractWebSvc;
import org.solenopsis.ws.SecurityWebSvc;
import org.solenopsis.ws.StandardWebServiceWsdlEnum;
import org.solenopsis.ws.WebServiceTypeEnum;

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
