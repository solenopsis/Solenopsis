package org.solenopsis.metadata.wsimport;

import java.util.LinkedList;
import java.util.List;
import org.flossware.util.ParameterUtil;
import org.solenopsis.lasius.sforce.wsimport.metadata.DescribeMetadataObject;
import org.solenopsis.lasius.sforce.wsimport.metadata.FileProperties;
import org.solenopsis.lasius.sforce.wsimport.metadata.ListMetadataQuery;
import org.solenopsis.lasius.sforce.wsimport.metadata.MetadataPortType;
import org.solenopsis.metadata.Child;
import org.solenopsis.metadata.Root;

/**
 *
 * Defines the root values of metadata.
 *
 * @author sfloess
 *
 */
public class RootDescribeMetadataObject implements Root {
    protected static List<Child> process(final Root root, final List<FileProperties> filePropertiesList) {
        final List<Child> retVal = new LinkedList<Child>();

        for (final FileProperties fileProperties : filePropertiesList) {
            retVal.add(new ChildFileProperties(root, fileProperties));
        }

        return retVal;
    }

    protected static List<ListMetadataQuery> createMetaDataQuertyList(final String type, final String folder) {
        final ListMetadataQuery query = new ListMetadataQuery();
        query.setType(type);
        query.setFolder(folder);

        final List<ListMetadataQuery> metaDataQuertyList = new LinkedList<ListMetadataQuery>();
        metaDataQuertyList.add(query);

        return metaDataQuertyList;
    }

    protected static List<ListMetadataQuery> createMetaDataQuertyList(final String type) {
        final ListMetadataQuery query = new ListMetadataQuery();
        query.setType(type);

        final List<ListMetadataQuery> metaDataQuertyList = new LinkedList<ListMetadataQuery>();
        metaDataQuertyList.add(query);

        return metaDataQuertyList;
    }

    protected static List<Child> process(final Root root, final MetadataPortType metadataPort, List<ListMetadataQuery> metadataQueryList, final Double apiVersion) throws Exception {
        return process(root, metadataPort.listMetadata(metadataQueryList, apiVersion));
    }

    protected static List<Child> processNonfolderBased(final Root root, final MetadataPortType metadataPort, final DescribeMetadataObject dmo, final Double apiVersion) throws Exception {
        return process(root, metadataPort, createMetaDataQuertyList(dmo.getXmlName()), apiVersion);
    }

    protected static List<Child> processFolderBased(final Root root, final MetadataPortType metadataPort, final DescribeMetadataObject dmo, final Double apiVersion) throws Exception {
        final ListMetadataQuery query = new ListMetadataQuery();
        query.setType(("EmailTemplate".equals(dmo.getXmlName()) ? "Email" : dmo.getXmlName())+"Folder");

        final List<ListMetadataQuery> metaDataQuertyList = new LinkedList<ListMetadataQuery>();
        metaDataQuertyList.add(query);

        final List<Child> retVal = new LinkedList<Child>();

        for (final FileProperties fileProperties : metadataPort.listMetadata(metaDataQuertyList, apiVersion)) {
            retVal.addAll(process(root, metadataPort, createMetaDataQuertyList(dmo.getXmlName(), fileProperties.getFullName()), apiVersion));
        }

        return retVal;
    }

    private final DescribeMetadataObject describeMetadataObject;
    private final List<Child> childList;

    protected DescribeMetadataObject getDescribeMetadataObject() {
        return describeMetadataObject;
    }

    public RootDescribeMetadataObject(final MetadataPortType metadataPort, final DescribeMetadataObject describeMetadataObject, final Double apiVersion) throws Exception {
        ParameterUtil.ensureParameter(metadataPort,           "Metadata port cannot be null!");
        ParameterUtil.ensureParameter(describeMetadataObject, "Describe metadata result cannot be null!");

        this.describeMetadataObject = describeMetadataObject;
        this.childList              = (describeMetadataObject.isInFolder() ? processFolderBased(this, metadataPort, describeMetadataObject, apiVersion) : processNonfolderBased(this, metadataPort, describeMetadataObject, apiVersion));
    }

    @Override
    public String getDirectoryName() {
        return getDescribeMetadataObject().getDirectoryName();
    }

    @Override
    public String getSuffix() {
        return getDescribeMetadataObject().getSuffix();
    }
    @Override
    public String getXmlName() {
        return getDescribeMetadataObject().getXmlName();
    }

    @Override
    public boolean isMetaFile() {
        return getDescribeMetadataObject().isMetaFile();
    }

    @Override
    public List<Child> getChildren() {
        return childList;
    }
}
