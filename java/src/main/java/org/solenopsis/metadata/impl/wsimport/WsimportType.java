package org.solenopsis.metadata.impl.wsimport;

import java.util.LinkedList;
import java.util.List;
import org.flossware.util.ParameterUtil;
import org.solenopsis.lasius.sforce.wsimport.metadata.DescribeMetadataObject;
import org.solenopsis.lasius.sforce.wsimport.metadata.FileProperties;
import org.solenopsis.lasius.sforce.wsimport.metadata.ListMetadataQuery;
import org.solenopsis.lasius.sforce.wsimport.metadata.MetadataPortType;
import org.solenopsis.metadata.impl.AbstractType;
import org.solenopsis.metadata.wsimport.WsimportOrg;
import org.solenopsis.metadata.wsimport.WsimportType;

/**
 *
 * Defines the type values of metadata.
 *
 * @author sfloess
 *
 */
public class WsimportType extends AbstractType implements WsimportType {
    protected static List<WsimportMember> process(final List<FileProperties> filePropertiesList) {
        final List<WsimportMember> retVal = new LinkedList<WsimportMember>();

        for (final FileProperties fileProperties : filePropertiesList) {
            retVal.add(new WsimportMember(fileProperties));
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

    protected static List<WsimportMember> process(final MetadataPortType metadataPort, List<ListMetadataQuery> metadataQueryList, final Double apiVersion) throws Exception {
        return process(metadataPort.listMetadata(metadataQueryList, apiVersion));
    }

    protected static List<WsimportMember> processNonfolderBased(final MetadataPortType metadataPort, final DescribeMetadataObject dmo, final Double apiVersion) throws Exception {
        return process(metadataPort, createMetaDataQuertyList(dmo.getXmlName()), apiVersion);
    }

    protected static List<WsimportMember> processFolderBased(final MetadataPortType metadataPort, final DescribeMetadataObject dmo, final double apiVersion) throws Exception {
        ParameterUtil.ensureParameter(metadataPort, "Metadata port cannot be null!");
        ParameterUtil.ensureParameter(dmo,          "Describe metadata result cannot be null!");

        final ListMetadataQuery query = new ListMetadataQuery();
        query.setType(("EmailTemplate".equals(dmo.getXmlName()) ? "Email" : dmo.getXmlName())+"Folder");

        final List<ListMetadataQuery> metaDataQuertyList = new LinkedList<ListMetadataQuery>();
        metaDataQuertyList.add(query);

        final List<WsimportMember> retVal = new LinkedList<WsimportMember>();

        for (final FileProperties fileProperties : metadataPort.listMetadata(metaDataQuertyList, apiVersion)) {
            retVal.addAll(process(metadataPort, createMetaDataQuertyList(dmo.getXmlName(), fileProperties.getFullName()), apiVersion));
        }

        return retVal;
    }

    private WsimportOrg org;
    private final DescribeMetadataObject describeMetadataObject;

    void setOrg(final WsimportOrg org) {
        ParameterUtil.ensureParameter(org, "Cannot have a null org!");

        this.org = org;
    }

    WsimportType(final MetadataPortType metadataPort, final DescribeMetadataObject describeMetadataObject, final Double apiVersion) throws Exception {
        super(describeMetadataObject.isInFolder() ? processFolderBased(metadataPort, describeMetadataObject, apiVersion) : processNonfolderBased(metadataPort, describeMetadataObject, apiVersion));

        this.describeMetadataObject = describeMetadataObject;

        for(final WsimportMember member : getMembers()) {
            member.setType(this);
        }
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
    public boolean hasMetaFile() {
        return getDescribeMetadataObject().isMetaFile();
    }

    @Override
    public WsimportOrg getOrg() {
        return org;
    }

    @Override
    public DescribeMetadataObject getDescribeMetadataObject() {
        return describeMetadataObject;
    }
}
