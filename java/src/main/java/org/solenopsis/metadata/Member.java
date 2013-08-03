package org.solenopsis.metadata;

import org.flossware.util.Stringifiable;

/**
 *
 * Defines a member for a type.
 *
 * @author sfloess
 *
 */
public interface Member extends Stringifiable {
    Type getType();
    String getFullName();
    String getFileName();

    Member copy(Type type);
}
