package org.solenopsis.metadata;

import java.util.Comparator;
import org.flossware.util.ObjectFilter;

/**
 *
 * The purpose of this class is
 *
 * @author sfloess
 *
 */
public class DirectoryNameComparator implements Comparator<Type>, ObjectFilter<Type, String> {
    /**
     * @{@inheritDoc}
     */
    @Override
    public int compare(Type o1, Type o2) {
        return o1.getDirectoryName().compareTo(o2.getDirectoryName());
    }

    /**
     * @{@inheritDoc}
     */
    @Override
    public boolean isFiltered(final Type type, final String dirName) {
        return type.getDirectoryName().equals(dirName);
    }
}
