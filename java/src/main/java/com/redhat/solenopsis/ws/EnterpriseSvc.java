package com.redhat.solenopsis.ws;

import com.redhat.sforce.soap.enterprise.Soap;

/**
 *
 * Uses the enterprise wsdl for logins.
 *
 * @author sfloess
 *
 */
public interface EnterpriseSvc extends LoginSvc<Soap> {
}
