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
public interface Org extends Stringifiable {
    Collection<Type> getMetadata();

    Collection<Member> getAllMembers();

    Member getMember(String fileName);

    boolean containsMember(String fileName);
}
