package org.solenopsis.metadata;

import org.flossware.util.Stringifiable;

/**
 *
 * Defines a loose definition of metadata for collection and grouping.
 *
 * @author sfloess
 *
 */
public interface MetadataCollectable<P extends MetadataCollection> extends Stringifiable {
    /**
     * Create a copy of self belonging to collection.
     *
     * @param <C> the type of self.
     * @param collection the collection for whom we will create a copy of self.
     *
     * @return a copy of self as a member of collection.
     */
    <T extends MetadataCollectable> T copy(P collection);

    /**
     * Return the collection for whom we are a member.
     *
     * @return the collection for whom we are a member.
     */
    P getOwnerCollection();
}
