package org.solenopsis.metadata.wsimport.impl;

import java.util.LinkedList;
import java.util.List;
import org.flossware.util.ParameterUtil;
import org.solenopsis.lasius.sforce.wsimport.metadata.DescribeMetadataObject;
import org.solenopsis.lasius.sforce.wsimport.metadata.FileProperties;
import org.solenopsis.lasius.sforce.wsimport.metadata.ListMetadataQuery;
import org.solenopsis.lasius.sforce.wsimport.metadata.MetadataPortType;
import org.solenopsis.metadata.Member;
import org.solenopsis.metadata.Type;
import org.solenopsis.metadata.impl.AbstractType;

/**
 *
 * Defines the type values of metadata.
 *
 * @author sfloess
 *
 */
public class DefaultWsimportType extends AbstractType {
    protected static List<Member> process(final Type root, final List<FileProperties> filePropertiesList) {
        final List<Member> retVal = new LinkedList<Member>();

        for (final FileProperties fileProperties : filePropertiesList) {
            retVal.add(new DefaultWsimportMember(root, fileProperties));
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

    protected static List<Member> process(final Type root, final MetadataPortType metadataPort, List<ListMetadataQuery> metadataQueryList, final Double apiVersion) throws Exception {
        return process(root, metadataPort.listMetadata(metadataQueryList, apiVersion));
    }

    protected static List<Member> processNonfolderBased(final Type root, final MetadataPortType metadataPort, final DescribeMetadataObject dmo, final Double apiVersion) throws Exception {
        return process(root, metadataPort, createMetaDataQuertyList(dmo.getXmlName()), apiVersion);
    }

    protected static List<Member> processFolderBased(final Type root, final MetadataPortType metadataPort, final DescribeMetadataObject dmo, final Double apiVersion) throws Exception {
        final ListMetadataQuery query = new ListMetadataQuery();
        query.setType(("EmailTemplate".equals(dmo.getXmlName()) ? "Email" : dmo.getXmlName())+"Folder");

        final List<ListMetadataQuery> metaDataQuertyList = new LinkedList<ListMetadataQuery>();
        metaDataQuertyList.add(query);

        final List<Member> retVal = new LinkedList<Member>();

        for (final FileProperties fileProperties : metadataPort.listMetadata(metaDataQuertyList, apiVersion)) {
            retVal.addAll(process(root, metadataPort, createMetaDataQuertyList(dmo.getXmlName(), fileProperties.getFullName()), apiVersion));
        }

        return retVal;
    }

    private final DescribeMetadataObject describeMetadataObject;
    private final List<Member> memberList;

    protected DescribeMetadataObject getDescribeMetadataObject() {
        return describeMetadataObject;
    }

    public DefaultWsimportType(final MetadataPortType metadataPort, final DescribeMetadataObject describeMetadataObject, final Double apiVersion) throws Exception {
        ParameterUtil.ensureParameter(metadataPort,           "Metadata port cannot be null!");
        ParameterUtil.ensureParameter(describeMetadataObject, "Describe metadata result cannot be null!");

        this.describeMetadataObject = describeMetadataObject;
        this.memberList             = (describeMetadataObject.isInFolder() ? processFolderBased(this, metadataPort, describeMetadataObject, apiVersion) : processNonfolderBased(this, metadataPort, describeMetadataObject, apiVersion));
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
    public List<Member> getMembers() {
        return memberList;
    }
}
