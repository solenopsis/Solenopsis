package org.solenopsis.metadata.comparator;

import java.util.Comparator;
import org.flossware.util.ObjectFilter;
import org.solenopsis.metadata.Member;

/**
 *
 * The purpose of this class is
 *
 * @author sfloess
 *
 */
public class FullNameComparator implements Comparator<Member>, ObjectFilter<Member, String> {
    /**
     * @{@inheritDoc}
     */
    @Override
    public int compare(Member o1, Member o2) {
        return o1.getFullName().compareTo(o2.getFullName());
    }

    /**
     * @{@inheritDoc}
     */
    @Override
    public boolean isFiltered(final Member member, final String fullName) {
        return member.getFullName().equals(fullName);
    }
}
