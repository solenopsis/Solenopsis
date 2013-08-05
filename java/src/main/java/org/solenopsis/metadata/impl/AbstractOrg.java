package org.solenopsis.metadata.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import org.flossware.util.CollectionUtil;
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
    private final Set<Type> typeSet;

    protected Set<Type> getTypeSet() {
        return typeSet;
    }

    protected AbstractOrg() {
        this.typeSet = new HashSet<>();
    }

    protected AbstractOrg(final Org toCopy) {
        this();

        ParameterUtil.ensureParameter(toCopy, "Cannot copy a null org!");

        for (final Type type : toCopy.getTypes()) {
            this.typeSet.add(ParameterUtil.ensureParameter(type, "Cannot copy a null type!").copy(this));
        }
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
    public Collection<Type> getByXmlTypes() {
        return CollectionUtil.sort(getTypeSet(), XML_NAME_COMPARATOR);
    }

    /**
     * @{@inheritDoc}
     */
    @Override
    public Collection<Type> getByDirTypes() {
        return CollectionUtil.sort(getTypeSet(), DIRECTORY_NAME_COMPARATOR);
    }

    /**
     * @{@inheritDoc}
     */
    @Override
    public Type getByXmlName(final String xmlName) {
        return CollectionUtil.find(getTypeSet(), XML_NAME_COMPARATOR, ParameterUtil.ensureParameter(xmlName, "XML name cannot be null or empty"));
    }

    /**
     * @{@inheritDoc}
     */
    @Override
    public Type getByDirName(final String dirName) {
        return CollectionUtil.find(getTypeSet(), DIRECTORY_NAME_COMPARATOR, ParameterUtil.ensureParameter(xmlName, "XML name cannot be null or empty"));
    }

    /**
     * @{@inheritDoc}
     */
    @Override
    public Type addType(final Type type) {
        if (!getTypeSet().contains(ParameterUtil.ensureParameter(type, "Cannot have a null type!"))) {
            final Type toCopy = type.copy(this);

            getTypeSet().add(toCopy);

            return toCopy;
        }

        final Type toAdd = getTypeSet().contains(ParameterUtil.ensureParameter(type, "Cannot have a null type!").copy(this);

        getXmlMap().put(type.getXmlName(),       toCopy);
        getDirMap().put(type.getDirectoryName(), toCopy);

        return toCopy;
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

        if (null == type) {
            type = member.getType().copy(this);

            final String directoryName = ParameterUtil.ensureParameter(type.getDirectoryName(), "Directory name cannot be null!");
            final String xmlName       = ParameterUtil.ensureParameter(type.getXmlName(),       "XML name cannot be null!");

            getDirMap().put(directoryName, type);
            getXmlMap().put(xmlName,       type);
        }

        return type.add(member);
    }

    /**
     * @{@inheritDoc}
     */
    @Override
    public boolean containsFileName(final String fileName) {
        return (null != getByFileName(fileName));
    }

    /**
     * @{@inheritDoc}
     */
    @Override
    public boolean containsFullName(final String fullName) {
        return (null != getByFullName(fullName));
    }
}
