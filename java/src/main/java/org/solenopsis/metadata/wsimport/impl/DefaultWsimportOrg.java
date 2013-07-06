package org.solenopsis.metadata.wsimport.impl;

import java.util.LinkedList;
import java.util.List;
import org.solenopsis.lasius.sforce.wsimport.metadata.DescribeMetadataResult;
import org.solenopsis.lasius.sforce.wsimport.metadata.MetadataPortType;
import org.solenopsis.metadata.Type;
import org.solenopsis.lasius.sforce.wsimport.metadata.DescribeMetadataObject;
import org.solenopsis.metadata.impl.AbstractOrg;

/**
 *
 * Represents an org's metadata
 *
 * @author sfloess
 *
 */
public class DefaultWsimportOrg extends AbstractOrg {
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

    protected static List<Type> createMetadata(final MetadataPortType metadataPort, final double apiVersion) throws Exception {
        return createMetadata(metadataPort, metadataPort.describeMetadata(apiVersion), apiVersion);
    }

    protected static List<Type> createMetadata(final MetadataPortType metadataPort, final String apiVersion) throws Exception {
        return createMetadata(metadataPort, Double.parseDouble(apiVersion));
    }

    private final List<Type> metadataList;

    public DefaultWsimportOrg(final MetadataPortType metadataPort, final String apiVersion) throws Exception {
        metadataList = createMetadata(metadataPort, apiVersion);
    }

    @Override
    public List<Type> getMetadata() {
        return metadataList;
    }
}
