package org.solenopsis.metadata.impl;

import org.solenopsis.metadata.Member;

/**
 *
 * Abstract base class for child metadata.
 *
 * @author sfloess
 *
 */
public abstract class AbstractMember extends AbstractMetadata implements Member {
    /**
     * @{@inheritDoc}
     */
    @Override
    public void toString(final StringBuilder stringBuilder, final String prefix) {
        stringBuilder.append(prefix).append("Fullame:    ").append(getFullName()).append(LINE_SEPARATOR_STRING);
        stringBuilder.append(prefix).append("File name:  ").append(getFileName()).append(LINE_SEPARATOR_STRING);
    }
}
