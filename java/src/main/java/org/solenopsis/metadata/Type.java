package org.solenopsis.metadata;

import java.util.Collection;
import org.flossware.util.Stringifiable;

/**
 *
 * Defines a metadata type.
 *
 * @author sfloess
 *
 */
public interface Type<M extends Member> extends Stringifiable {
    String getDirectoryName();
    String getSuffix();
    String getXmlName();
    boolean hasMetaFile();

    <O extends Org<M, Type<M>>> O getOrg();

    Collection<M> getMembers();
}
