package com.redhat.solenopsis.ws;

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
        "/wsdl/metadata.wsdl", "http://soap.sforce.com/2006/04/metadata", "MetadataService"
    ),
    
    ENTERPRISE_SERVICE (
        "/wsdl/enterprise.wsdl", "urn:enterprise.soap.sforce.com", "SforceService"
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
     * @param namespaceURI The namespace for the QName
     * @param localPart The local part for the QName.
     */
    private ServiceEnum (final String wsdlResource, final String namespaceURI, final String localPart) {
        this.wsdlResource = wsdlResource;
        this.namespaceURI = namespaceURI;
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
