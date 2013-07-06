package org.solenopsis.metadata.wsimport.impl;

import java.util.LinkedList;
import java.util.List;
import org.solenopsis.lasius.sforce.wsimport.metadata.DescribeMetadataResult;
import org.solenopsis.lasius.sforce.wsimport.metadata.MetadataPortType;
import org.solenopsis.metadata.Type;
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
public class DefaultWsimportOrg extends AbstractOrg implements WsimportOrg {
    protected static List<Type> createMetadata(final MetadataPortType metadataPort, final List<DescribeMetadataObject> metadataObjects, final double apiVersion) throws Exception {
        final List<Type> retVal = new LinkedList<Type>();

        for (final DescribeMetadataObject dmo : metadataObjects) {
            retVal.add(new DefaultWsimportType(metadataPort, dmo, apiVersion));
        }

        return retVal;
    }

    protected static List<Type> createMetadata(final MetadataPortType metadataPort, final DescribeMetadataResult describeMetadataResult, final double apiVersion) throws Exception {
        return createMetadata(metadataPort, describeMetadataResult.getMetadataObjects(), apiVersion);
    }

    private final DescribeMetadataResult describeMetadataResult;

    private final List<Type> metadataList;

    public DefaultWsimportOrg(final MetadataPortType metadataPort, final String apiVersion) throws Exception {
        final double api = Double.parseDouble(apiVersion);

        this.describeMetadataResult = metadataPort.describeMetadata(api);
        this.metadataList           = createMetadata(metadataPort, describeMetadataResult, api);
    }

    @Override
    public List<Type> getMetadata() {
        return metadataList;
    }

    @Override
    public DescribeMetadataResult getDescribeMetadataResult() {
        return describeMetadataResult;
    }
}
