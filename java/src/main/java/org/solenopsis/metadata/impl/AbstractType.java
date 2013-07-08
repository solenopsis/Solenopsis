package org.solenopsis.metadata.impl;

import java.util.Collection;
import java.util.Collections;
import org.flossware.util.ParameterUtil;
import org.solenopsis.metadata.Member;
import org.solenopsis.metadata.Org;
import org.solenopsis.metadata.Type;

/**
 *
 * Abstract base class for root metadata.
 *
 * @author sfloess
 *
 */
public abstract class AbstractType extends AbstractMetadata implements Type {
    private Org org;

    private final String directoryName;
    private final String suffix;
    private final String xmlName;
    private final boolean hasMetaFile;

    public String getDirectoryName();
    public String getSuffix();
    public String getXmlName();
    public boolean hasMetaFile();

    private final Collection<Member> members;

    protected AbstractType(final Collection<Member> members) {
        ParameterUtil.ensureParameter(members, "Cannot have null metadata!");

        this.members = Collections.unmodifiableCollection(members);
    }

    protected void setOrg(final Org org) {
        ParameterUtil.ensureParameter(org, "Org cannot be null");

        this.org = org;
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
    public Collection<Member> getMembers() {
        return members;
    }
}
