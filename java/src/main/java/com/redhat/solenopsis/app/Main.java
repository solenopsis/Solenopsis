package com.redhat.solenopsis.app;

import com.redhat.sforce.soap.enterprise.LoginResult;
import com.redhat.sforce.soap.metadata.*;
import com.redhat.solenopsis.util.PackageXml;
import com.redhat.solenopsis.ws.Enterprise;
import com.redhat.solenopsis.ws.Metadata;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 *
 * The purpose of this class is
 *
 * @author sfloess
 *
 */
public class Main {
    public static void main(final String[] args) throws Exception {
        final String env = "dev.properties";
        
        final FileInputStream fis = new FileInputStream (System.getProperty("user.home") + "/.solenopsis/credentials/" + env);
        final Properties props = new Properties();
        props.load(fis);
        
        final String url = (props.getProperty("url") != null ? props.getProperty("url") : "https://test.salesforce.com");

        //final LoginResult loginResult = Enterprise.login(props.getProperty("username"), props.getProperty("password"), props.getProperty("token"), "https://test.salesforce.com/services/Soap/c/24.0");
        
        final LoginResult loginResult = Enterprise.login(props.getProperty("username"), props.getProperty("password"), props.getProperty("token"), url);
        final MetadataPortType port = Metadata.getPort(loginResult);
        final DescribeMetadataResult describeMetadata = port.describeMetadata(24);
        
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
            }
            
            
                    
            final ListMetadataQuery query = new ListMetadataQuery();
            query.setType(dmo.getXmlName());
            if (null != dmo.getDirectoryName() || ! "".equals(dmo.getDirectoryName())) {
                query.setFolder(dmo.getDirectoryName());
            }

            final List<ListMetadataQuery> metaDataQuertyList = new ArrayList<ListMetadataQuery>();  
            
            metaDataQuertyList.add(query);
        
            System.out.println();

            final List<FileProperties> filePropertiesList = port.listMetadata(metaDataQuertyList, 24);
            for (final FileProperties fileProperties : filePropertiesList) {
                System.out.println ("Full name:      " + fileProperties.getFullName());
                System.out.println ("     file name: " + fileProperties.getFileName());
                System.out.println ("     type:      " + fileProperties.getType());
            }
        
            System.out.println("\n\n");
            
            System.out.println(PackageXml.computePackage(describeMetadata));
            
        }          

    }
}
