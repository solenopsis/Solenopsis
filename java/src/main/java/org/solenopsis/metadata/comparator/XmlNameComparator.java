package org.solenopsis.metadata.comparator;

import java.util.Comparator;
import org.flossware.util.ObjectFilter;
import org.solenopsis.metadata.Type;

/**
 *
 * The purpose of this class is
 *
 * @author sfloess
 *
 */
public class XmlNameComparator implements Comparator<Type>, ObjectFilter<Type, String> {
    /**
     * @{@inheritDoc}
     */
    @Override
    public int compare(Type o1, Type o2) {
        return o1.getXmlName().compareTo(o2.getXmlName());
    }

    /**
     * @{@inheritDoc}
     */
    @Override
    public boolean isFiltered(final Type type, final String xmlName) {
        return type.getXmlName().equals(xmlName);
    }
}
