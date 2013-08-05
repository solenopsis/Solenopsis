package org.solenopsis.metadata;

import java.util.Collection;
import org.flossware.util.Stringifiable;

/**
 *
 * The purpose of this interface is
 *
 * @author sfloess
 *
 */
public interface MemberCollection extends Stringifiable {
    public static FullNameComparator FULL_NAME_COMPARATOR = new FullNameComparator();
    public static FileNameComparator FILE_NAME_COMPARATOR = new FileNameComparator();

    Collection<Member> getByFileNames();
    Collection<Member> getByFullNames();
    Collection<Member> getMembers();

    Member getByFileName(String fileName);
    Member getByFullName(String fullName);

    Member addMember(Member member);
    Collection<Member> addMembers(Type type);

    boolean containsFileName(String fileName);
    boolean containsFullName(String fullName);
}
