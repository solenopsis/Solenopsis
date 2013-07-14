package org.solenopsis.metadata.impl;

import java.util.Collection;
import java.util.Collections;
import org.flossware.util.ParameterUtil;
import org.solenopsis.metadata.Member;
import org.solenopsis.metadata.Type;

/**
 *
 * Abstract base class for root metadata.
 *
 * @author sfloess
 *
 */
public abstract class AbstractType<M extends Member> extends AbstractMetadata implements Type<M> {
    private final Collection<M> members;

    protected AbstractType(final Collection<M> members) {
        ParameterUtil.ensureParameter(members, "Cannot have null metadata!");

        this.members = Collections.unmodifiableCollection(members);
    }

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

    @Override
    public Collection<M> getMembers() {
        return members;
    }
}
