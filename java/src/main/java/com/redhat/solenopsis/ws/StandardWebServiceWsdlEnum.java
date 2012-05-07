package com.redhat.solenopsis.ws;

import java.net.URL;
import javax.xml.namespace.QName;

/**
 *
 * These are constants used by the standard SFDC web services such as metadata,
 * enterprise and partner WSDLs.
 *
 * @author sfloess
 *
 */
public enum StandardWebServiceWsdlEnum {
    METADATA_SERVICE (
        "/wsdl/metadata.wsdl", "http://soap.sforce.com/2006/04/metadata", "MetadataService"
    ),
    
    ENTERPRISE_SERVICE (
        "/wsdl/enterprise.wsdl", "urn:enterprise.soap.sforce.com", "SforceService"
    ),
    
    PARTNER_SERVICE (
        "/wsdl/partner.wsdl", "urn:partner.soap.sforce.com", "SforceService"
    );
    
    /**
     * The WSDL for the SFDC web service.
     */
    private final URL wsdlResource;
    
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
     * @param namespaceURI The namespace for the QName.
     * @param localPart The local part for the QName.
     */
    private StandardWebServiceWsdlEnum (final String wsdlResource, final String namespaceURI, final String localPart) {
        this.wsdlResource = getClass().getClass().getResource(wsdlResource);
        this.namespaceURI = namespaceURI;
        this.localPart    = localPart;   
        this.qname        = new QName(namespaceURI, localPart);
    }
    
    /**
     * The WSDL for the SFDC web service.
     */
    public URL getWsdlResource() {
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
    
    /**
     * Using <code>sb</code>, compute the string representation of self whose
     * prefix for computation is <code>prefix</code>.
     * 
     * @param sb will have the string representation of self appended.
     * @param prefix is the prefix to be first appended prior to self's string representation.
     */
    public void toString(final StringBuilder sb, final String prefix) {
        sb.append(prefix).append("localPart    [").append(getLocalPart()).append("]\n");
        sb.append(prefix).append("namespaceURI [").append(getNamespaceURI()).append("]\n");
        sb.append(prefix).append("wsdlResource [").append(getWsdlResource()).append("]\n");
        sb.append(prefix).append("qname        [").append(getQName()).append("]");
    }
    
    /**
     * Using <code>sb</code>, compute the string representation of self.
     * 
     * @param sb will have the string representation of self appended.
     */
    public void toString(final StringBuilder sb) {
        toString(sb, "");
    }
    
    /**
     * Using <code>prefix</code> return the string representation of self.
     * 
     * @param prefix is the prefix to emit when return the string representation of self.
     * 
     * @return the string representation of self.
     */
    public String toString(final String prefix) {
        final StringBuilder sb = new StringBuilder();
        
        toString(sb, prefix);
        
        return sb.toString();
    }
    
    /**
     * Return the string representation of self.
     * 
     * @return the string representation of self.
     */
    @Override
    public String toString() {
        return toString("");
    }    
}
