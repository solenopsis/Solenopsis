package org.solenopsis.metadata;

import java.util.Comparator;

/**
 *
 * The purpose of this class is
 *
 * @author sfloess
 *
 */
public class FileNameComparator implements Comparator<Member> {
    @Override
    public int compare(Member o1, Member o2) {
        return o1.getFileName().compareTo(o2.getFileName());
    }
}
