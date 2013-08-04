package org.solenopsis.metadata;

import java.util.Comparator;

/**
 *
 * The purpose of this class is
 *
 * @author sfloess
 *
 */
public class XmlNameComparator implements Comparator<Type> {
    @Override
    public int compare(Type o1, Type o2) {
        return o1.getXmlName().compareTo(o2.getXmlName());
    }
}
