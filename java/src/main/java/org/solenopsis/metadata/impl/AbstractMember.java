package org.solenopsis.metadata.impl;

import org.flossware.util.ParameterUtil;
import org.solenopsis.metadata.Member;
import org.solenopsis.metadata.Type;
import static org.solenopsis.metadata.impl.AbstractMetadata.LINE_SEPARATOR_STRING;

/**
 *
 * Abstract base class for child metadata.
 *
 * @author sfloess
 *
 */
public abstract class AbstractMember extends AbstractMetadata implements Member {
    private final Type type;
    private final String fullName;
    private final String fileName;

    protected AbstractMember(final Type type, final String fullName, final String fileName) {
        ParameterUtil.ensureParameter(type,     "Type cannot be null!");
        ParameterUtil.ensureParameter(fullName, "Full name cannot be null or empty!");
        ParameterUtil.ensureParameter(fileName, "File name cannot be null or empty!");

        this.type     = type;
        this.fullName = fullName;
        this.fileName = fileName;
    }

    protected AbstractMember(final Type type, final Member toCopy) {
        this(type, ParameterUtil.ensureParameter(toCopy).getFullName(), toCopy.getFileName());
    }

    /**
     * @{@inheritDoc}
     */
    @Override
    public void toString(final StringBuilder stringBuilder, final String prefix) {
        stringBuilder.append(prefix).append("Fullame:    ").append(getFullName()).append(LINE_SEPARATOR_STRING);
        stringBuilder.append(prefix).append("File name:  ").append(getFileName()).append(LINE_SEPARATOR_STRING);
    }

    /**
     * @{@inheritDoc}
     */
    @Override
    public Type getType() {
        return type;
    }

    /**
     * @{@inheritDoc}
     */
    @Override
    public String getFullName() {
        return fullName;
    }

    /**
     * @{@inheritDoc}
     */
    @Override
    public String getFileName() {
        return fileName;
    }
}
