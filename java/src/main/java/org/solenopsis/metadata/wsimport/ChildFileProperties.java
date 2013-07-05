package org.solenopsis.metadata.wsimport;

import org.flossware.util.ParameterUtil;
import org.solenopsis.lasius.sforce.wsimport.metadata.FileProperties;
import org.solenopsis.metadata.Child;
import org.solenopsis.metadata.Root;

/**
 *
 * The purpose of this interface is
 *
 * @author sfloess
 *
 */
public class ChildFileProperties implements Child {
    private final Root root;
    private final FileProperties fileProperties;

    protected FileProperties getFileProperties() {
        return fileProperties;
    }

    public ChildFileProperties(final Root root, final FileProperties fileProperties) {
        ParameterUtil.ensureParameter(root,           "Root cannot be null!");
        ParameterUtil.ensureParameter(fileProperties, "File properties cannot be null!");

        this.root           = root;
        this.fileProperties = fileProperties;
    }

    @Override
    public String getFullName() {
        return getFileProperties().getFullName();
    }

    @Override
    public String getFileName() {
        return getFileProperties().getFileName();
    }

    @Override
    public Root getRoot() {
        return root;
    }
}
