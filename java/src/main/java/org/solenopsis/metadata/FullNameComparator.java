package org.solenopsis.metadata;

import java.util.Comparator;

/**
 *
 * The purpose of this class is
 *
 * @author sfloess
 *
 */
public class FullNameComparator implements Comparator<Member> {
    @Override
    public int compare(Member o1, Member o2) {
        return o1.getFullName().compareTo(o2.getFullName());
    }
}
