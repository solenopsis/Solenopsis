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
public abstract class AbstractOrg<M extends Member, T extends Type<M>> extends AbstractMetadata implements Org<M, T> {
    private final Collection<T> metadata;
    private final Map<String, M> membersMap;
    private final Collection<M> allMembers;

    protected static <M extends Member, T extends Type<M>> Map<String, M> createMap(final Collection<T> metadataList) {
        final Map<String, M> retVal = new TreeMap<String, M>();

        for(final Type<M> metadata : metadataList) {
            for (final M member : metadata.getMembers()) {
                retVal.put(member.getFileName(), member);
            }
        }

        return retVal;
    }

    protected Map<String, M> getMembersMap() {
        return membersMap;
    }

    protected AbstractOrg(final Collection<T> metadata) {
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
    public Collection<T> getMetadata() {
        return metadata;
    }

    @Override
    public Collection<M> getAllMembers() {
        return allMembers;
    }

    @Override
    public M getMember(final String fileName) {
        return getMembersMap().get(fileName);
    }

    @Override
    public boolean containsMember(final String fileName) {
        return getMembersMap().containsKey(fileName);
    }
}
