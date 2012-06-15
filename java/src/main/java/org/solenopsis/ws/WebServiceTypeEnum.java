package com.redhat.solenopsis.ws;

/**
 *
 * The type of SFDC web service.  When calling to SFDC, we need the SFDC base url
 * (https://test.salesforce.com or https://login.salesforce.com) plus the suffix
 * found here.  Additionally, for the metadata service, and enterprise/partner wsdls,
 * we need the API version.  For custom web service (developer written), we need the
 * name of the web service itself.
 * 
 * The following table illustrates this:
 *           BASE URL               SERVICE TYPE SUFFIX   SPECIFIC TO SERVICE
 *     https://test.salesforce.com    services/Soap/m             21.0
 *     https://login.salesforce.com   services/Soap/c             22.0
 *     https://test.salesforce.com    services/Soap/u             24.0
 *     https://login.salesforce.com   service/Soap/class          FooAPI
 *
 * Examples:
 *     https://test.salesforce.com/services/Soap/m/21.0
 *     https://login.salesforce.com/services/Soap/c/22.0
 *     https://test.salesforce.com/services/Soap/u/24.0
 *     https://login.salesforce.com/service/Soap/class/FooAPI
 * 
 * @author sfloess
 *
 */
public enum WebServiceTypeEnum {
    METADATA_SERVICE("services/Soap/m"),    
    ENTERPRISE_SERVICE("services/Soap/c"),    
    PARTNER_SERVICE("services/Soap/u"),
    CUSTOM_SERVICE("services/Soap/class");      // Use this for your custom web services you build in Apex.
    
    /**
     * Append this onto the base URL.
     */
    private final String urlSuffix;
    
    /**
     * Constructor.
     *
     * @param urlSuffix Appends to the base URL.
     */
    private WebServiceTypeEnum (final String urlSuffix) {
        this.urlSuffix = urlSuffix;
    }

    /**
     * Return the url suffix that will be appended onto the base url.
     */
    public String getUrlSuffix() {
        return urlSuffix;
    }
    
    /**
     * Using <code>sb</code>, compute the string representation of self whose
     * prefix for computation is <code>prefix</code>.
     * 
     * @param sb will have the string representation of self appended.
     * @param prefix is the prefix to be first appended prior to self's string representation.
     */
    public void toString(final StringBuilder sb, final String prefix) {
        sb.append(prefix).append("urlSuffix [").append(getUrlSuffix()).append("]");
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
