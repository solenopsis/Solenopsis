package org.solenopsis.metadata.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;
import org.flossware.util.ParameterUtil;
import org.solenopsis.metadata.Member;
import org.solenopsis.metadata.Org;
import org.solenopsis.metadata.Type;

/**
 *
 * Abstract base class for org metadata.
 *
 * @author sfloess
 *
 */
public abstract class AbstractOrg extends AbstractMetadata implements Org {
    private final Collection<Type> metadata;
    private final Map<String, Member> membersMap;
    private final Collection<Member> allMembers;

    protected static Map<String, Member> createMap(final Collection<Type> metadataList) {
        final Map<String, Member> retVal = new TreeMap<String, Member>();

        for(final Type metadata : metadataList) {
            for (final Member member : metadata.getMembers()) {
                retVal.put(member.getFileName(), member);
            }
        }

        return retVal;
    }

    protected Map<String, Member> getMembersMap() {
        return membersMap;
    }

    protected AbstractOrg(final Collection<Type> metadata) {
        ParameterUtil.ensureParameter(metadata, "Cannot have null metadata!");

        this.metadata   = Collections.unmodifiableCollection(metadata);
        this.membersMap = createMap(metadata);

        this.allMembers = Collections.unmodifiableCollection(membersMap.values());
    }

    /**
     * @{@inheritDoc}
     */
    @Override
    public void toString(final StringBuilder stringBuilder, final String prefix) {
        stringBuilder.append(prefix).append("Children(").append(getMetadata().size()).append("):").append(LINE_SEPARATOR_STRING);

        final String memberPrefix = prefix + "    ";

        for (final Type orgType : getMetadata()) {
            orgType.toString(stringBuilder, memberPrefix);
        }
    }

    @Override
    public Collection<Type> getMetadata() {
        return metadata;
    }

    @Override
    public Collection<Member> getAllMembers() {
        return allMembers;
    }

    @Override
    public Member getMember(final String fileName) {
        return getMembersMap().get(fileName);
    }

    @Override
    public boolean containsMember(final String fileName) {
        return getMembersMap().containsKey(fileName);
    }
}
