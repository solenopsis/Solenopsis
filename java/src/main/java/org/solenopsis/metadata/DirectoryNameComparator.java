package org.solenopsis.metadata;

import java.util.Comparator;

/**
 *
 * The purpose of this class is
 *
 * @author sfloess
 *
 */
public class DirectoryNameComparator implements Comparator<Type> {
    @Override
    public int compare(Type o1, Type o2) {
        return o1.getDirectoryName().compareTo(o2.getDirectoryName());
    }
}
