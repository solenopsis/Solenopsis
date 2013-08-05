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
public interface TypeCollection extends Stringifiable {
    public static final DirectoryNameComparator DIRECTORY_NAME_COMPARATOR = new DirectoryNameComparator();
    public static final XmlNameComparator XML_NAME_COMPARATOR = new XmlNameComparator();

    Collection<Type> getByXmlTypes();
    Collection<Type> getByDirTypes();
    Collection<Type> getTypes();

    Type getByXmlName(String xmlName);
    Type getByDirName(String dirName);
}
