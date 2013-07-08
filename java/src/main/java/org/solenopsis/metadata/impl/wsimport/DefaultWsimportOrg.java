package org.solenopsis.metadata.impl.wsimport;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import org.solenopsis.lasius.sforce.wsimport.metadata.DescribeMetadataResult;
import org.solenopsis.lasius.sforce.wsimport.metadata.MetadataPortType;
import org.solenopsis.lasius.sforce.wsimport.metadata.DescribeMetadataObject;
import org.solenopsis.metadata.impl.AbstractOrg;
import org.solenopsis.metadata.wsimport.WsimportOrg;

/**
 *
 * Represents an org's metadata
 *
 * @author sfloess
 *
 */
public class DefaultWsimportOrg extends AbstractOrg<DefaultWsimportMember, DefaultWsimportType> implements WsimportOrg<DefaultWsimportMember, DefaultWsimportType> {
    protected static List<DefaultWsimportType> createMetadata(final MetadataPortType metadataPort, final List<DescribeMetadataObject> metadataObjects, final double apiVersion) throws Exception {
        final List<DefaultWsimportType> retVal = new LinkedList<DefaultWsimportType>();

        for (final DescribeMetadataObject dmo : metadataObjects) {
            retVal.add(new DefaultWsimportType(metadataPort, dmo, apiVersion));
        }

        return retVal;
    }

    protected static List<DefaultWsimportType> createMetadata(final MetadataPortType metadataPort, final DescribeMetadataResult describeMetadataResult, final double apiVersion) throws Exception {
        return createMetadata(metadataPort, describeMetadataResult.getMetadataObjects(), apiVersion);
    }

    private final DescribeMetadataResult describeMetadataResult;

    private DefaultWsimportOrg(final MetadataPortType metadataPort, final double apiVersion, final DescribeMetadataResult describeMetadataResult) throws Exception {
        super(createMetadata(metadataPort, describeMetadataResult, apiVersion));

        this.describeMetadataResult = describeMetadataResult;

        for(final DefaultWsimportType member : getMetadata()) {
            member.setOrg(this);
        }
    }

    public DefaultWsimportOrg(final MetadataPortType metadataPort, final double apiVersion) throws Exception {
        this(metadataPort, apiVersion, metadataPort.describeMetadata(apiVersion));
    }

    public DefaultWsimportOrg(final MetadataPortType metadataPort, final String apiVersion) throws Exception {
        this(metadataPort, Double.parseDouble(apiVersion));
    }

    @Override
    public DescribeMetadataResult getDescribeMetadataResult() {
        return describeMetadataResult;
    }
}
