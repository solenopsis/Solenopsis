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
    public static final FileNameComparator FILE_NAME_COMPARATOR = new FileNameComparator();
    public static final FullNameComparator FULL_NAME_COMPARATOR = new FullNameComparator();

    Collection<Member> getByFileNames();
    Collection<Member> getByFullNames();
    Collection<Member> getMembers();

    Member getByFileName(String fileName);
    Member getByFullName(String fullName);

    Member add(Member member);

    boolean containsFileName(String fileName);
    boolean containsFullName(String fullName);
}
