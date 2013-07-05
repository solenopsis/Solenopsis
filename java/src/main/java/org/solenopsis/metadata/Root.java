package org.solenopsis.metadata;

import java.util.List;

/**
 *
 * Defines the root values of a metadata type.
 *
 * @author sfloess
 *
 */
public interface Root {
    String getDirectoryName();
    String getSuffix();
    String getXmlName();
    boolean isMetaFile();

    List<Child> getChildren();
}
