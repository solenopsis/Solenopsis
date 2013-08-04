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

    protected AbstractMember(final Type type) {
        this.type = ParameterUtil.ensureParameter(type, "Type cannot be null!");
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
    public boolean equals(final Object obj) {
        if (null == obj || !(obj instanceof Member)) {
            return false;
        }

        final Member member = (Member) obj;

        return member.getFullName().equals(getFullName()) &&
               member.getFileName().equals(getFileName());
    }

    /**
     * @{@inheritDoc}
     */
    @Override
    public int hashCode() {
        return getFullName().hashCode() + getFileName().hashCode();
    }

    /**
     * @{@inheritDoc}
     */
    @Override
    public Type getType() {
        return type;
    }
}
