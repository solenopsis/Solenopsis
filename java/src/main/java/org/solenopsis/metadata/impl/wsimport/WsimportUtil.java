package org.solenopsis.metadata.impl.wsimport;

import java.util.List;
import org.solenopsis.lasius.sforce.wsimport.metadata.DescribeMetadataObject;
import org.solenopsis.lasius.sforce.wsimport.metadata.DescribeMetadataResult;
import org.solenopsis.lasius.sforce.wsimport.metadata.MetadataPortType;
import org.solenopsis.metadata.wsimport.WsimportOrg;

/**
 *
 * The purpose of this class is
 *
 * @author sfloess
 *
 */
public final class WsimportUtil {
    public static WsimportOrg createOrg(final MetadataPortType metadataPort, final double apiVersion, final List<DescribeMetadataObject> metadataObjects) throws Exception {
        final WsimportOrg org = new WsimportOrg();

        for (final DescribeMetadataObject dmo : metadataObjects) {
            org.add(new WsimportType(metadataPort, dmo, apiVersion));
        }

        return org;
    }

    public static WsimportOrg createOrg(final MetadataPortType metadataPort, final double apiVersion, final DescribeMetadataResult describeMetadataResult) throws Exception {
        return createOrg(metadataPort, apiVersion, describeMetadataResult.getMetadataObjects());
    }

    public static WsimportOrg createOrg(final MetadataPortType metadataPort, final double apiVersion) throws Exception {
        return createOrg(metadataPort, apiVersion, metadataPort.describeMetadata(apiVersion));
    }

    public static WsimportOrg createOrg(final MetadataPortType metadataPort, final String apiVersion) throws Exception {
        return createOrg(metadataPort, Double.parseDouble(apiVersion));
    }
}
