package org.solenopsis.metadata.impl;

import org.solenopsis.metadata.comparator.DirectoryNameComparator;
import org.solenopsis.metadata.comparator.XmlNameComparator;
import java.util.Collection;
import java.util.HashSet;
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
public abstract class AbstractOrg extends AbstractMetadataCollection<Type> implements Org {
    public static final DirectoryNameComparator DIRECTORY_NAME_COMPARATOR = new DirectoryNameComparator();
    public static final XmlNameComparator XML_NAME_COMPARATOR = new XmlNameComparator();

    protected static void copyTypes(final Org toCopy, final AbstractOrg copyTo) {
        for (final Type type : ParameterUtil.ensureParameter(toCopy, "Cannot copy a null org!").getTypes()) {
            copyTo.typeSet.add(ParameterUtil.ensureParameter(type, "Cannot copy a null type!").copy(copyTo));
        }
    }

    protected AbstractOrg() {
    }

    protected AbstractOrg(final Org toCopy) {
        this();

        copyTypes(toCopy, this);
    }


    /**
     * @{@inheritDoc}
     */
    @Override
    public Collection<Type> getByXmlTypes() {
        return getSortedCollection(XML_NAME_COMPARATOR);
    }

    /**
     * @{@inheritDoc}
     */
    @Override
    public Collection<Type> getByDirTypes() {
        return getSortedCollection(DIRECTORY_NAME_COMPARATOR);
    }

    /**
     * @{@inheritDoc}
     */
    @Override
    public Type getByXmlName(final String xmlName) {
        return findForValue(XML_NAME_COMPARATOR, xmlName);
    }

    /**
     * @{@inheritDoc}
     */
    @Override
    public Type getByDirName(final String dirName) {
        return findForValue(DIRECTORY_NAME_COMPARATOR, dirName);
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

        return type;
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
    public Member addMember(final Member member) {
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
