package org.solenopsis.metadata;

/**
 *
 * Defines a member for a type.
 *
 * @author sfloess
 *
 */
public interface Member extends MetadataCollectable<Type> {
    Type getType();
    String getFullName();
    String getFileName();
}
