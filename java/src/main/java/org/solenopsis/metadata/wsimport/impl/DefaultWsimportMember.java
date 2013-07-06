package org.solenopsis.metadata.wsimport.impl;

import org.flossware.util.ParameterUtil;
import org.solenopsis.lasius.sforce.wsimport.metadata.FileProperties;
import org.solenopsis.metadata.Type;
import org.solenopsis.metadata.impl.AbstractMember;

/**
 *
 * The purpose of this interface is
 *
 * @author sfloess
 *
 */
public class DefaultWsimportMember extends AbstractMember {
    private final Type root;
    private final FileProperties fileProperties;

    protected FileProperties getFileProperties() {
        return fileProperties;
    }

    public DefaultWsimportMember(final Type root, final FileProperties fileProperties) {
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
    public Type getType() {
        return root;
    }
}
