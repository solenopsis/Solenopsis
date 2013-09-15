package org.solenopsis.metadata;

import java.util.Collection;

/**
 *
 * Defines a metadata type.
 *
 * @author sfloess
 *
 */
public interface Type extends MetadataCollection<Member>, MetadataCollectable<Org> {
    Collection<Member> getByFileNames();
    Collection<Member> getByFullNames();

    Member getByFileName(String fileName);
    Member getByFullName(String fullName);

    Member addMember(Member member);
    Collection<Member> addMembers(Type type);

    boolean containsFileName(String fileName);
    boolean containsFullName(String fullName);

    String getDirectoryName();
    String getSuffix();
    String getXmlName();
    boolean hasMetaFile();

    Org getOrg();
}
