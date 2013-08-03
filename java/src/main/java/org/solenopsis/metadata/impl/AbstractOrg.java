package org.solenopsis.metadata.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
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
    private Map<String, Type> xmlMap;
    private Map<String, Type> dirMap;

    protected Map<String, Type> getXmlMap() {
        return xmlMap;
    }

    protected Map<String, Type> getDirMap() {
        return dirMap;
    }

    protected AbstractOrg(final Collection<Type> typeCollection) {
        ParameterUtil.ensureParameter(typeCollection, "Cannot have null types!");

        this.xmlMap = new TreeMap<>();
        this.dirMap = new TreeMap<>();

        for (final Type type : typeCollection) {
            final Type typeCopy = type.copy(this);

            this.xmlMap.put(type.getXmlName(),       typeCopy);
            this.dirMap.put(type.getDirectoryName(), typeCopy);
        }
    }

    protected AbstractOrg(final Org toCopy) {
        this(ParameterUtil.ensureParameter(toCopy, "Cannot copy a null org!").getXmlTypes());
    }

    /**
     * @{@inheritDoc}
     */
    @Override
    public void toString(final StringBuilder stringBuilder, final String prefix) {
        stringBuilder.append(prefix).append("Children(").append(getXmlMap().size()).append("):").append(LINE_SEPARATOR_STRING);

        final String memberPrefix = prefix + "    ";

        for (final Type orgType : getXmlMap().values()) {
            orgType.toString(stringBuilder, memberPrefix);
        }
    }

    /**
     * @{@inheritDoc}
     */
    @Override
    public Collection<Type> getXmlTypes() {
        return Collections.unmodifiableCollection(getXmlMap().values());
    }

    /**
     * @{@inheritDoc}
     */
    @Override
    public Collection<Type> getDirTypes() {
        return Collections.unmodifiableCollection(getDirMap().values());
    }

    /**
     * @{@inheritDoc}
     */
    @Override
    public Type getTypeByXmlName(final String xmlName) {
        return getXmlMap().get(ParameterUtil.ensureParameter(xmlName, "XML name cannot be null or empty"));
    }

    /**
     * @{@inheritDoc}
     */
    @Override
    public Type getTypeByDirName(final String dirName) {
        return getDirMap().get(ParameterUtil.ensureParameter(dirName, "Dir name cannot be null or empty"));
    }

    /**
     * @{@inheritDoc}
     */
    @Override
    public Type addType(final Type type) {
        final Type toCopy = ParameterUtil.ensureParameter(type, "Cannot have a null type!").copy(this);

        getXmlMap().put(type.getXmlName(),       toCopy);
        getDirMap().put(type.getDirectoryName(), toCopy);

        return toCopy;
    }

    /**
     * @{@inheritDoc}
     */
    @Override
    public Collection<Member> getMembers() {
        Collection<Member> retVal = new LinkedList<Member>();

        for (final Type type : getXmlMap().values()) {
            retVal.addAll(type.getMembers());
        }

        return retVal;
    }

    /**
     * @{@inheritDoc}
     */
    @Override
    public Member getByFileName(final String fileName) {
        for (final Type type : getXmlMap().values()) {
            final Member member = type.getByFileName(fileName);
            if (null != member) {
                return member;
            }
        }

        return null;
    }

    /**
     * @{@inheritDoc}
     */
    @Override
    public Member add(final Member member) {
        Type type = getDirMap().get(ParameterUtil.ensureParameter(member, "Cannot add a null member!").getType().getDirectoryName());

        if (null != type) {
            return type.add(member);
        }

        type = member.getType().copy(this);

        getDirMap().put(type.getDirectoryName(), type);
        getXmlMap().put(type.getXmlName(),       type);

        return member.copy(type);
    }

    /**
     * @{@inheritDoc}
     */
    @Override
    public boolean containsMember(final String fileName) {
        return (null != getByFileName(fileName));
    }
}
