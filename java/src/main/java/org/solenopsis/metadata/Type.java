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
public interface Type extends Stringifiable {
    String getDirectoryName();
    String getSuffix();
    String getXmlName();
    boolean hasMetaFile();

    Org getOrg();

    Collection<Member> getMembers();
}
