package org.solenopsis.metadata.wsimport;

import java.util.LinkedList;
import org.solenopsis.metadata.Org;
import java.util.List;
import org.solenopsis.lasius.sforce.wsimport.metadata.DescribeMetadataResult;
import org.solenopsis.lasius.sforce.wsimport.metadata.ListMetadataQuery;
import org.solenopsis.lasius.sforce.wsimport.metadata.MetadataPortType;
import org.solenopsis.metadata.Root;
import org.solenopsis.lasius.sforce.wsimport.metadata.DescribeMetadataObject;

/**
 *
 * Represents an org's metadata
 *
 * @author sfloess
 *
 */
public class OrgDescribeMetadataObject implements Org {
    protected static List<Root> createMetadata(final MetadataPortType metadataPort, final List<DescribeMetadataObject> metadataObjects, final double apiVersion) throws Exception {
        final List<Root> retVal = new LinkedList<Root>();

        for (final DescribeMetadataObject dmo : metadataObjects) {
            retVal.add(new RootDescribeMetadataObject(metadataPort, dmo, apiVersion));
        }

        return retVal;
    }

    protected static List<Root> createMetadata(final MetadataPortType metadataPort, final DescribeMetadataResult describeMetadataResult, final double apiVersion) throws Exception {
        return createMetadata(metadataPort, describeMetadataResult, apiVersion);
    }

    protected static List<Root> createMetadata(final MetadataPortType metadataPort, final double apiVersion) throws Exception {
        return createMetadata(metadataPort, metadataPort.describeMetadata(apiVersion), apiVersion);
    }

    protected static List<Root> createMetadata(final MetadataPortType metadataPort, final String apiVersion) throws Exception {
        return createMetadata(metadataPort, Double.parseDouble(apiVersion));
    }

    private final List<Root> metadataList;

    public OrgDescribeMetadataObject(final MetadataPortType metadataPort, final String apiVersion) throws Exception {
        metadataList = createMetadata(metadataPort, apiVersion);
    }

    @Override
    public List<Root> getMetadata() {
        return metadataList;
    }
}
