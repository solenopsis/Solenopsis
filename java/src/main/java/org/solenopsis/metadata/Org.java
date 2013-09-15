package org.solenopsis.metadata;

import java.util.Collection;

/**
 *
 * Represents an org's metadata
 *
 * @author sfloess
 *
 */
public interface Org extends MetadataCollection<Type> {
    Collection<Type> getByXmlTypes();
    Collection<Type> getByDirTypes();
    Collection<Type> getTypes();

    Type getByXmlName(String xmlName);
    Type getByDirName(String dirName);

    Org copy();
}
