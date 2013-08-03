package org.solenopsis.metadata;

/**
 *
 * Represents an org's metadata
 *
 * @author sfloess
 *
 */
public interface Org extends TypeCollection, MemberCollection {
    Org copy();
}
