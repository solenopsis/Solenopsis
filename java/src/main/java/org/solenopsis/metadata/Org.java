package org.solenopsis.metadata;

import java.util.Collection;
import org.flossware.util.Stringifiable;

/**
 *
 * Represents an org's metadata
 *
 * @author sfloess
 *
 */
public interface Org<M extends Member, T extends Type<M>> extends Stringifiable {
    Collection<T> getMetadata();

    Collection<M> getAllMembers();

    M getMember(String fileName);

    boolean containsMember(String fileName);
}
