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
    Collection<Type> getXmlTypes();
    Collection<Type> getDirTypes();

    Type getTypeByXmlName(String xmlName);
    Type getTypeByDirName(String dirName);

    Type addType(Type type);
}
