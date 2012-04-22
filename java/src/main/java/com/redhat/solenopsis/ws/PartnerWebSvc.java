package com.redhat.solenopsis.ws;

import com.redhat.sforce.soap.partner.Soap;

/**
 *
 * Uses the partner wsdl for logins.
 *
 * @author sfloess
 *
 */
public interface PartnerWebSvc extends LoginWebSvc<Soap> {
}
