package org.solenopsis.metadata;

/**
 *
 * Defines a metadata type.
 *
 * @author sfloess
 *
 */
public interface Type extends MemberCollection {
    String getDirectoryName();
    String getSuffix();
    String getXmlName();
    boolean hasMetaFile();

    Org getOrg();

    Type copy(Org org);
}
