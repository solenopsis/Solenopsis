package org.solenopsis.util;

import org.solenopsis.sforce.soap.metadata.DescribeMetadataObject;
import org.solenopsis.sforce.soap.metadata.DescribeMetadataResult;
import org.solenopsis.ws.StandardWebServiceWsdlEnum;

/**
 *
 * The package.xml utility functions.
 *
 * @author sfloess
 *
 */
public class PackageXml {
    public static void computeNamesElement(final StringBuilder sb, DescribeMetadataObject describeMetadataObject ) {
        sb.append("        <name>").append(describeMetadataObject.getXmlName()).append("</name>\n");
    }
    
    public static void computeMembersElement(final StringBuilder sb, DescribeMetadataObject describeMetadataObject ) {
        sb.append("        <members>*</members>\n");
    }
    
    public static void computeTypesElement(final StringBuilder sb, DescribeMetadataObject describeMetadataObject ) {
        sb.append("    <types>\n");
        
        computeMembersElement(sb, describeMetadataObject);
        computeNamesElement(sb, describeMetadataObject);        
        
        sb.append("    </types>\n");
    }
    
    public static void computePackage(final StringBuilder sb, final DescribeMetadataResult describeMetadataResult) {  
        sb.append("<package xmlns=\"").append(StandardWebServiceWsdlEnum.METADATA_SERVICE.getNamespaceURI()).append("\">\n");
        
        for (final DescribeMetadataObject describeMetadataObject : describeMetadataResult.getMetadataObjects()) {
            computeTypesElement(sb, describeMetadataObject);
        }
        
        sb.append("</package>");
    }
    
    public static String computePackage(final DescribeMetadataResult describeMetadataResult) {
        final StringBuilder sb = new StringBuilder();
        
        computePackage(sb, describeMetadataResult);
        
        return sb.toString();
    }
}
