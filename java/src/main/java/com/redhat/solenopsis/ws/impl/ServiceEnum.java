package com.redhat.solenopsis.ws.impl;

import javax.xml.namespace.QName;

/**
 *
 * Define the constants for using SFDC services.
 *
 * @author sfloess
 *
 */
public enum ServiceEnum {
    METADATA_SERVICE (
        "/wsdl/metadata.wsdl", "http://soap.sforce.com/2006/04/metadata", "services/Soap/m", "MetadataService"
    ),
    
    ENTERPRISE_SERVICE (
        "/wsdl/enterprise.wsdl", "urn:enterprise.soap.sforce.com", "ervices/Soap/c", "SforceService"
    ),
    
    PARTNER_SERVICE (
        "/wsdl/partner.wsdl", "urn:partner.soap.sforce.com", "services/Soap/u", "SforceService"
    );
    
    /**
     * The WSDL for the SFDC web service.
     */
    private final String wsdlResource;
    
    /**
     * The namespace for the QName...
     */
    private final String namespaceURI;
    
    /**
     * Append this onto the base URL.
     */
    private final String urlSuffix;
    
    /**
     * The local part for the QName.
     */
    private final String localPart;
    
    /**
     * The QName.
     */
    private final QName qname;
    
    /**
     * Constructor.
     *
     * @param wsdlResource The WSDL for the SFDC web service.
     * @param namespaceURI The namespace for the QName.
     * @param urlSuffix Appends to the base URL.
     * @param localPart The local part for the QName.
     */
    private ServiceEnum (final String wsdlResource, final String urlSuffix, final String namespaceURI, final String localPart) {
        this.wsdlResource = wsdlResource;
        this.namespaceURI = namespaceURI;
        this.urlSuffix    = urlSuffix;
        this.localPart    = localPart;
        
        this.qname        = new QName(namespaceURI, localPart);
    }
    
    /**
     * The WSDL for the SFDC web service.
     */
    public String getWsdlResource() {
        return wsdlResource;
    }
    
    /**
     * The namespace for the QName...
     */    
    public String getNamespaceURI() {
        return namespaceURI;
    }
    
    /**
     * Return the url suffix that will be appended onto the base url.
     */
    public String getUrlSuffix() {
        return urlSuffix;
    }
    
    /**
     * The local part for the QName.
     */    
    public String getLocalPart() {
        return localPart;
    }
    
    /**
     * The QName.
     */    
    public QName getQName() {
        return qname;
    }
}
