package org.solenopsis.app;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.LogManager;
import org.flossware.util.properties.impl.FilePropertiesMgr;
import org.solenopsis.lasius.credentials.Credentials;
import org.solenopsis.lasius.credentials.impl.PropertiesCredentials;
import org.solenopsis.lasius.sforce.wsimport.metadata.*;
import org.solenopsis.lasius.wsimport.security.SecurityMgr;
import org.solenopsis.lasius.wsimport.security.impl.PartnerSecurityMgr;
import org.solenopsis.lasius.wsimport.util.SalesforceWebServiceUtil;


/**
 *
 * The purpose of this class is emit metadata to the console.
 *
 * @author sfloess
 *
 */
public class DumpMetadata {
    public static void emitMetadata1(final MetadataPortType metadataPort, final String apiVersion) throws Exception {
        final double version = Double.parseDouble(apiVersion);

        final DescribeMetadataResult describeMetadata = metadataPort.describeMetadata(version);

        final List<DescribeMetadataObject> metadataObjects = describeMetadata.getMetadataObjects();

        int index = 0;

        final List<ListMetadataQuery> metaDataQuertyList = new ArrayList<ListMetadataQuery>();

        for (final DescribeMetadataObject dmo : metadataObjects) {
            index++;

            System.out.println("==============================================");

            System.out.println("Dir:       " + dmo.getDirectoryName());
            System.out.println("Suffix:    " + dmo.getSuffix());
            System.out.println("XML:       " + dmo.getXmlName());
            System.out.println("In folder: " + dmo.isInFolder());
            System.out.println("Meta file: " + dmo.isMetaFile());

            if (dmo.isInFolder()) {
                final ListMetadataQuery query = new ListMetadataQuery();

                // EmailTemplate is broken - need to use EmailFolder...
                query.setType(("EmailTemplate".equals(dmo.getXmlName()) ? "Email" : dmo.getXmlName())+"Folder");

                query.setFolder(dmo.getDirectoryName());

                metaDataQuertyList.add(query);
            } else {
                for (final String child : dmo.getChildXmlNames()) {
                    System.out.println("          " + child);

                    if (null != child && ! "".equals(child)) {
                        final ListMetadataQuery query = new ListMetadataQuery();
                        query.setType(child);

                        metaDataQuertyList.add(query);
                    }
                }
            }
        }

        System.out.println("Querying...");

        final List<FileProperties> filePropertiesList = metadataPort.listMetadata(metaDataQuertyList, version);

        System.out.println("Got back [" + filePropertiesList.size() + "]");

        for (final FileProperties fileProperties : filePropertiesList) {
            System.out.println ("            Full name:      " + fileProperties.getFullName());
            System.out.println ("                 file name: " + fileProperties.getFileName());
            System.out.println ("                 type:      " + fileProperties.getType());
        };


        System.out.println("\n\n");

        //System.out.println(PackageXml.computePackage(describeMetadata));
    }

    public static void emitMetadata(final MetadataPortType metadataPort, final String apiVersion) throws Exception {
        final double version = Double.parseDouble(apiVersion);

        final DescribeMetadataResult describeMetadata = metadataPort.describeMetadata(version);

        final List<DescribeMetadataObject> metadataObjects = describeMetadata.getMetadataObjects();

        int index = 0;

        for (final DescribeMetadataObject dmo : metadataObjects) {
            index++;

            System.out.println("==============================================");

            System.out.println("Dir:       " + dmo.getDirectoryName());
            System.out.println("Suffix:    " + dmo.getSuffix());
            System.out.println("XML:       " + dmo.getXmlName());
            System.out.println("In folder: " + dmo.isInFolder());
            System.out.println("Meta file: " + dmo.isMetaFile());

            if (dmo.isInFolder()) {
                System.out.println("    Child XML (" + dmo.getChildXmlNames().size() + "):");

                for(final String xmlName : dmo.getChildXmlNames()) {
                    System.out.println("        Child [" + xmlName + "]");
                }

                final ListMetadataQuery query = new ListMetadataQuery();

                // EmailTemplate is broken - need to use EmailFolder...
                query.setType(("EmailTemplate".equals(dmo.getXmlName()) ? "Email" : dmo.getXmlName())+"Folder");
                //query.setFolder(dmo.getDirectoryName());
                //query.setFolder("*");

                final List<ListMetadataQuery> metaDataQuertyList = new ArrayList<ListMetadataQuery>();

                metaDataQuertyList.add(query);

                try {
                    final List<FileProperties> filePropertiesList = metadataPort.listMetadata(metaDataQuertyList, version);
                    System.out.println("***Children (" + filePropertiesList.size() + "):");
                    for (final FileProperties fileProperties : filePropertiesList) {
                        System.out.println ("            Full name:      " + fileProperties.getFullName());
                        System.out.println ("                 file name: " + fileProperties.getFileName());
                        System.out.println ("                 type:      " + fileProperties.getType());

                        final ListMetadataQuery query1 = new ListMetadataQuery();

                        System.out.println("Setting type to [" + dmo.getXmlName() + "]");
                        query1.setType(dmo.getXmlName());
                        System.out.println("Setting folder to [" + fileProperties.getFullName() + "]");
                        query1.setFolder(fileProperties.getFullName());

                        final List<ListMetadataQuery> metaDataQuertyList1 = new ArrayList<ListMetadataQuery>();
                        metaDataQuertyList1.add(query1);

                        try {
                            final List<FileProperties> filePropertiesList1 = metadataPort.listMetadata(metaDataQuertyList1, version);
                            for (final FileProperties fileProperties1 : filePropertiesList1) {
                                System.out.println ("                 Full name:      " + fileProperties1.getFullName());
                                System.out.println ("                      file name: " + fileProperties1.getFileName());
                                System.out.println ("                      type:      " + fileProperties1.getType());
                            }
                        } catch (final Exception oops) {
                            System.out.println ("                 Problem:  " + oops.getMessage());
                        }
                    }

                } catch (final Exception e) {
                    System.out.println ("            Problem:  " + e.getMessage());
                }
            } else {
                System.out.println("Children (" + dmo.getChildXmlNames().size() + "):");
                for (final String child : dmo.getChildXmlNames()) {
                    System.out.println("          " + child);

                    if (null != child && ! "".equals(child)) {
                        final ListMetadataQuery query = new ListMetadataQuery();
                        query.setType(child);

                        final List<ListMetadataQuery> metaDataQuertyList = new ArrayList<ListMetadataQuery>();

                        metaDataQuertyList.add(query);

                        try {
                            final List<FileProperties> filePropertiesList = metadataPort.listMetadata(metaDataQuertyList, version);
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
            }


            System.out.println("\n\n");

            //System.out.println(PackageXml.computePackage(describeMetadata));
        }
    }


    public static void emitMetadata(final String msg, final Credentials credentials, final SecurityMgr securityWebSvc) throws Exception {
        System.out.println();

        System.out.println(msg);

        emitMetadata(SalesforceWebServiceUtil.createMetadataPort(securityWebSvc.login(credentials)), credentials.getApiVersion());
    }

    public static void main(final String[] args) throws Exception {
        LogManager.getLogManager().readConfiguration(DumpMetadata.class.getResourceAsStream("/logging.properties"));

        final String env = "test-dev.properties";
//        final String env = "dev.properties";


        Credentials credentials = new PropertiesCredentials(new FilePropertiesMgr(System.getProperty("user.home") + "/.solenopsis/credentials/" + env));

        emitMetadata("Partner WSDL", credentials, new PartnerSecurityMgr());
        //emitMetadata("Enterprise WSDL", credentials, new EnterpriseSecurityMgr());
    }
}
