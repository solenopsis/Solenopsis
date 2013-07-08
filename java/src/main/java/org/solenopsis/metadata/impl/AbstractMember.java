package org.solenopsis.metadata.impl;

import org.flossware.util.ParameterUtil;
import org.solenopsis.metadata.Member;
import org.solenopsis.metadata.Type;

/**
 *
 * Abstract base class for child metadata.
 *
 * @author sfloess
 *
 */
public abstract class AbstractMember extends AbstractMetadata implements Member {
    private Type metadataType;
    private final String fullName;
    private final String fileName;

    protected AbstractMember(final String fullName, final String fileName) {
        this.fullName = fullName;
        this.fileName = fileName;
    }

    protected void setType(final Type metadataType) {
        ParameterUtil.ensureParameter(metadataType, "Metadata type cannot be null!");
    }

    @Override
    public Type getType() {
        return metadataType;
    }

    @Override
    public String getFullName() {
        return fullName;
    }

    @Override
    public String getFileName() {
        return fileName;
    }

    @Override
    public void toString(final StringBuilder stringBuilder, final String prefix) {
        stringBuilder.append(prefix).append("Fullame:    ").append(getFullName()).append(LINE_SEPARATOR_STRING);
        stringBuilder.append(prefix).append("File name:  ").append(getFileName()).append(LINE_SEPARATOR_STRING);
    }
}
