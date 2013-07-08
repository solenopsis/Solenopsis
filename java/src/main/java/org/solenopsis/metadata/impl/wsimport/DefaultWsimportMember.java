package org.solenopsis.metadata.impl.wsimport;

import org.flossware.util.ParameterUtil;
import org.solenopsis.lasius.sforce.wsimport.metadata.FileProperties;
import org.solenopsis.metadata.Type;
import org.solenopsis.metadata.impl.AbstractMember;
import org.solenopsis.metadata.wsimport.WsimportMember;

/**
 *
 * The purpose of this interface is
 *
 * @author sfloess
 *
 */
public class DefaultWsimportMember extends AbstractMember implements WsimportMember {
    private DefaultWsimportType root;
    private final FileProperties fileProperties;

    void setType(final DefaultWsimportType root) {
        ParameterUtil.ensureParameter(root, "Type cannot be null!");

        this.root = root;
    }

    DefaultWsimportMember(final FileProperties fileProperties) {
        ParameterUtil.ensureParameter(fileProperties, "File properties cannot be null!");

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

    @Override
    public FileProperties getFileProperties() {
        return fileProperties;
    }
}
