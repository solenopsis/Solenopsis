package org.solenopsis.metadata.wsimport;

import org.solenopsis.lasius.sforce.wsimport.metadata.DescribeMetadataObject;
import org.solenopsis.metadata.Type;

/**
 *
 * Defines the root values of metadata.
 *
 * @author sfloess
 *
 */
public interface WsimportType<M extends WsimportMember> extends Type<M> {
    DescribeMetadataObject getDescribeMetadataObject();
}
