package com.redhat.solenopsis.ws.impl.bak;

import com.redhat.solenopsis.ws.impl.ServiceEnum;
import java.net.URL;
import javax.xml.namespace.QName;

/**
 *
 * Defines a service context which includes the credentials, 
 *
 * @author sfloess
 *
 */
public class ServiceContext { 
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
    
    public ServiceContext(final URL wsdlResource, final String namespaceURI, final String localPart) {
        this.wsdlResource = wsdlResource;
        this.namespaceURI = namespaceURI;
        this.localPart    = localPart;        
        this.qname        = new QName(namespaceURI, localPart);
    }
    
    /**
     * Constructor.
     *
     * @param wsdlResource The WSDL for the SFDC web service.
     * @param namespaceURI The namespace for the QName.
     * @param localPart The local part for the QName.
     */
    public ServiceContext (final String wsdlResource, final String namespaceURI, final String localPart) {
        this.wsdlResource = getClass().getResource(wsdlResource);
        this.namespaceURI = namespaceURI;
        this.localPart    = localPart;        
        this.qname        = new QName(namespaceURI, localPart);                
    }
    
    public ServiceContext(final ServiceEnum serviceEnum) {
        this (serviceEnum.getWsdlResource(), serviceEnum.getNamespaceURI(), serviceEnum.getLocalPart());
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
        sb.append(prefix).append("qname        [").append(getQName()).append("]\n");
        sb.append(prefix).append("wsdlResource [").append(getWsdlResource()).append("]");
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
