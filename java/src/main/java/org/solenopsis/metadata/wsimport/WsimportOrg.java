package org.solenopsis.metadata.wsimport;

import org.solenopsis.metadata.Org;
import org.solenopsis.lasius.sforce.wsimport.metadata.DescribeMetadataResult;

/**
 *
 * Represents an org's metadata
 *
 * @author sfloess
 *
 */
public interface WsimportOrg<M extends WsimportMember, T extends WsimportType<M>> extends Org<M, T> {
    DescribeMetadataResult getDescribeMetadataResult();
}
