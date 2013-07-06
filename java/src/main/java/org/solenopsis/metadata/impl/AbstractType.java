package org.solenopsis.metadata.impl;

import org.solenopsis.metadata.Member;
import org.solenopsis.metadata.Type;

/**
 *
 * Abstract base class for root metadata.
 *
 * @author sfloess
 *
 */
public abstract class AbstractType extends AbstractMetadata implements Type {
    /**
     * @{@inheritDoc}
     */
    @Override
    public void toString(final StringBuilder stringBuilder, final String prefix) {
        stringBuilder.append(prefix).append("Dir name:   ").append(getDirectoryName()).append(LINE_SEPARATOR_STRING);
        stringBuilder.append(prefix).append("Suffix:     ").append(getSuffix()).append(LINE_SEPARATOR_STRING);
        stringBuilder.append(prefix).append("XML name:   ").append(getXmlName()).append(LINE_SEPARATOR_STRING);
        stringBuilder.append(prefix).append("Meta file:  ").append(hasMetaFile()).append(LINE_SEPARATOR_STRING);
        stringBuilder.append(prefix).append("Children(").append(getMembers().size()).append("):").append(LINE_SEPARATOR_STRING);

        final String memberPrefix = prefix + "    ";

        for (final Member member : getMembers()) {
            member.toString(stringBuilder, memberPrefix);
        }
    }
}
