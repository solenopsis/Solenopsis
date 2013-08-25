package org.solenopsis.metadata;

import java.util.Collection;

/**
 *
 * The purpose of this interface is to define the API for a metadata collection.
 *
 * @author sfloess
 *
 */
public interface MetadataCollection<T extends Metadata> extends Metadata {
    /**
     * Return the internal collection.
     *
     * @return the internal collection.
     */
    Collection<T> getCollection();
}
