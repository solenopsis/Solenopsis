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
public interface WsimportOrg extends Org {
    DescribeMetadataResult getMetadataResult();
}
