package com.redhat.solenopsis.app;

import com.redhat.sforce.soap.metadata.DescribeMetadataObject;
import com.redhat.sforce.soap.metadata.DescribeMetadataResult;
import com.redhat.sforce.soap.metadata.FileProperties;
import com.redhat.sforce.soap.metadata.ListMetadataQuery;
import com.redhat.solenopsis.credentials.Credentials;
import com.redhat.solenopsis.credentials.impl.PropertiesCredentials;
import com.redhat.solenopsis.properties.impl.FileMonitorPropertiesMgr;
import com.redhat.solenopsis.properties.impl.FilePropertiesMgr;
import com.redhat.solenopsis.util.PackageXml;
import com.redhat.solenopsis.ws.LoginWebSvc;
import com.redhat.solenopsis.ws.MetadataWebSvc;
import com.redhat.solenopsis.ws.impl.DefaultEnterpriseWebSvc;
import com.redhat.solenopsis.ws.impl.DefaultMetadataWebSvc;
import com.redhat.solenopsis.ws.impl.DefaultPartnerWebSvc;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * The purpose of this class is
 *
 * @author sfloess
 *
 */
public class Main {
    public static void emitMetadata(final String msg, final LoginWebSvc loginSvc, final double apiVersion) throws Exception {
        MetadataWebSvc metadataSvc = new DefaultMetadataWebSvc(loginSvc);
        
        final DescribeMetadataResult describeMetadata = metadataSvc.getPort().describeMetadata(apiVersion);
        
        final List<DescribeMetadataObject> metadataObjects = describeMetadata.getMetadataObjects();                
        
        for (final DescribeMetadataObject dmo : metadataObjects) {
        
            System.out.println("==============================================");
            
            System.out.println("Dir:       " + dmo.getDirectoryName());
            System.out.println("Suffix:    " + dmo.getSuffix());
            System.out.println("XML:       " + dmo.getXmlName());
            System.out.println("In folder: " + dmo.isInFolder());
            System.out.println("Meta file: " + dmo.isMetaFile());
            System.out.println("Children:");
            for (final String child : dmo.getChildXmlNames()) {
                System.out.println("          " + child);
                
                if (null != child && ! "".equals(child)) {                
                    final ListMetadataQuery query = new ListMetadataQuery();
                    query.setType(child);   

                    final List<ListMetadataQuery> metaDataQuertyList = new ArrayList<ListMetadataQuery>();  

                    metaDataQuertyList.add(query);
                    
                    try {
                        final List<FileProperties> filePropertiesList = metadataSvc.getPort().listMetadata(metaDataQuertyList, 24);
                        for (final FileProperties fileProperties : filePropertiesList) {
                            System.out.println ("            Full name:      " + fileProperties.getFullName());
                            System.out.println ("                 file name: " + fileProperties.getFileName());
                            System.out.println ("                 type:      " + fileProperties.getType());
                        }
                    }
                    
                    catch(final Exception e) {
                        System.out.println ("            Problem:  " + e.getMessage());
                    }
                }
            }                        
                    
            final ListMetadataQuery query = new ListMetadataQuery();
            query.setType(dmo.getXmlName());
            if (null != dmo.getDirectoryName() || ! "".equals(dmo.getDirectoryName())) {
                query.setFolder(dmo.getDirectoryName());
            }

            final List<ListMetadataQuery> metaDataQuertyList = new ArrayList<ListMetadataQuery>();  
            
            metaDataQuertyList.add(query);
        
            System.out.println();

            final List<FileProperties> filePropertiesList = metadataSvc.getPort().listMetadata(metaDataQuertyList, 24);
            for (final FileProperties fileProperties : filePropertiesList) {
                System.out.println ("Full name:      " + fileProperties.getFullName());
                System.out.println ("     file name: " + fileProperties.getFileName());
                System.out.println ("     type:      " + fileProperties.getType());
            };            
        
            System.out.println("\n\n");
            
//            System.out.println(PackageXml.computePackage(describeMetadata));
            
        }

    }
        
    public static void main(final String[] args) throws Exception {
        //final String env = "prod.properties";
        final String env = "test-dev.properties";
        
        Credentials credentials = new PropertiesCredentials(new FilePropertiesMgr(System.getProperty("user.home") + "/.solenopsis/credentials/" + env));
        
        double apiVersion = Double.parseDouble(credentials.getApiVersion());
        
        emitMetadata("Enterprise WSDL", new DefaultEnterpriseWebSvc(credentials), apiVersion);
        //emitMetadata("Partner WSDL", new DefaultPartnerWebSvc(credentials), apiVersion);

    }
}
