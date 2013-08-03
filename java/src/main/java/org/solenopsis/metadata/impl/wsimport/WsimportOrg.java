package org.solenopsis.metadata.impl.wsimport;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import org.solenopsis.lasius.sforce.wsimport.metadata.DescribeMetadataResult;
import org.solenopsis.lasius.sforce.wsimport.metadata.MetadataPortType;
import org.solenopsis.lasius.sforce.wsimport.metadata.DescribeMetadataObject;
import org.solenopsis.metadata.Type;
import org.solenopsis.metadata.impl.AbstractOrg;
import org.solenopsis.metadata.wsimport.WsimportMember;
import org.solenopsis.metadata.wsimport.WsimportOrg;
import org.solenopsis.metadata.wsimport.WsimportType;

/**
 *
 * Represents an org's metadata
 *
 * @author sfloess
 *
 */
public class WsimportOrg extends AbstractOrg<WsimportType, WsimportMember> implements WsimportOrg {
    protected static List<WsimportType> createMetadata(final WsimportOrg t, final MetadataPortType metadataPort, final List<DescribeMetadataObject> metadataObjects, final double apiVersion) throws Exception {
        final List<WsimportType> retVal = new LinkedList<WsimportType>();

        for (final DescribeMetadataObject dmo : metadataObjects) {
            retVal.add(new WsimportType(metadataPort, dmo, apiVersion));
        }

        return retVal;
    }

    protected static List<WsimportType> createMetadata(final WsimportOrg t, final MetadataPortType metadataPort, final DescribeMetadataResult describeMetadataResult, final double apiVersion) throws Exception {
        return createMetadata(t, metadataPort, describeMetadataResult.getMetadataObjects(), apiVersion);
    }

    protected void add(final DescribeMetadataObject dmo) {
        super.add(new WsimportType(this, dmo));
    }

    private final DescribeMetadataResult describeMetadataResult;

    private WsimportOrg(final MetadataPortType metadataPort, final double apiVersion, final DescribeMetadataResult describeMetadataResult) throws Exception {
        super(createMetadata(this, metadataPort, describeMetadataResult, apiVersion));

        this.describeMetadataResult = describeMetadataResult;

        for(final WsimportType member : getMetadata()) {
            member.setOrg(this);
        }
    }

    public WsimportOrg(final MetadataPortType metadataPort, final double apiVersion) throws Exception {
        this(metadataPort, apiVersion, metadataPort.describeMetadata(apiVersion));
    }

    public WsimportOrg(final MetadataPortType metadataPort, final String apiVersion) throws Exception {
        this(metadataPort, Double.parseDouble(apiVersion));
    }

    @Override
    public DescribeMetadataResult getDescribeMetadataResult() {
        return describeMetadataResult;
    }
}
