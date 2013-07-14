package org.solenopsis.metadata.wsimport;

import org.solenopsis.lasius.sforce.wsimport.metadata.FileProperties;
import org.solenopsis.metadata.Member;

/**
 *
 * The purpose of this interface is
 *
 * @author sfloess
 *
 */
public interface WsimportMember extends Member {
    FileProperties getFileProperties();
}
