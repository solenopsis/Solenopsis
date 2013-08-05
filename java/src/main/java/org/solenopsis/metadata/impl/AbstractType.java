package org.solenopsis.metadata.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
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
 * Abstract base class for root metadata.
 *
 * @author sfloess
 *
 */
public abstract class AbstractType extends AbstractMetadata implements Type {
    private final Org org;

    private final Set<Member> memberSet;

    protected Set<Member> getMemberSet() {
        return memberSet;
    }

    protected Type ensureSameType(final Type type, final String errorMessage) {
        if (!equals(type)) {
            throw new IllegalArgumentException(errorMessage);
        }

        return type;
    }

    protected Member ensureSameType(final Member member, final String errorMessage) {
        ensureSameType(member.getType(), errorMessage);

        return member;
    }

    protected AbstractType(final Org org) {
        this.org = ParameterUtil.ensureParameter(org, "Org cannot be null!");
        this.memberSet = new HashSet<>();
    }

    protected AbstractType(final Org org, Type toCopy) {
        this(org);

        for (final Member member : toCopy.getMembers()) {
            this.memberSet.add(ParameterUtil.ensureParameter(member, "Cannot copy a null member!").copy(this));
        }
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
        stringBuilder.append(prefix).append("Children(").append(getMemberSet().size()).append("):").append(LINE_SEPARATOR_STRING);

        final String memberPrefix = prefix + "    ";

        for (final Member member : getMemberSet()) {
            member.toString(stringBuilder, memberPrefix);
        }
    }

    /**
     * @{@inheritDoc}
     */
    @Override
    public boolean equals(final Object obj) {
        if (null == obj || !(obj instanceof Type)) {
            return false;
        }

        final Type type = (Type) obj;

        return type.getDirectoryName().equals(getDirectoryName()) &&
               type.getSuffix().equals(getSuffix())               &&
               type.getXmlName().equals(getXmlName())             &&
               type.hasMetaFile() == hasMetaFile();
    }

    /**
     * @{@inheritDoc}
     */
    @Override
    public int hashCode() {
        return getDirectoryName().hashCode() + getSuffix().hashCode() + getXmlName().hashCode() + (hasMetaFile() ? 1 : 0);
    }

    /**
     * @{@inheritDoc}
     */
    @Override
    public Collection<Member> getByFileNames() {
        return CollectionUtil.sort(getMemberSet(), FILE_NAME_COMPARATOR);
    }

    /**
     * @{@inheritDoc}
     */
    @Override
    public Collection<Member> getByFullNames() {
        return CollectionUtil.sort(getMemberSet(), FULL_NAME_COMPARATOR);
    }

    /**
     * @{@inheritDoc}
     */
    @Override
    public Collection<Member> getMembers() {
        return Collections.unmodifiableCollection(getMemberSet());
    }

    /**
     * @{@inheritDoc}
     */
    @Override
    public Member getByFileName(final String fileName) {
        return CollectionUtil.find(getMemberSet(), FILE_NAME_COMPARATOR, ParameterUtil.ensureParameter(fileName, "File name cannot be null or empty!"));
    }

    /**
     * @{@inheritDoc}
     */
    @Override
    public Member getByFullName(final String fullName) {
        return CollectionUtil.find(getMemberSet(), FULL_NAME_COMPARATOR, ParameterUtil.ensureParameter(fullName, "Full name cannot be null or empty!"));
    }

    /**
     * @{@inheritDoc}
     */
    @Override
    public Member addMember(final Member member) {
        if (getMemberSet().contains(ParameterUtil.ensureParameter(member, "Cannot add a null member!"))) {
            return member;
        }

        final Member copy = ensureSameType(member, "Cannot add members from a different type!").copy(this);

        getMemberSet().add(copy);

        return copy;
    }

    /**
     * @{@inheritDoc}
     */
    @Override
    public Collection<Member> addMembers(final Type type) {
        ParameterUtil.ensureParameter(type, "Cannot add members from a null type!");

        ensureSameType(type, "Cannot add members from different types!");

        final Collection<Member> retVal = new LinkedList<>();

        for (final Member member : type.getMembers()) {
            retVal.add(addMember(member));
        }

        return retVal;
    }

    /**
     * @{@inheritDoc}
     */
    @Override
    public boolean containsFileName(final String fileName) {
        return CollectionUtil.contains(getMemberSet(), FILE_NAME_COMPARATOR, ParameterUtil.ensureParameter(fileName, "File name cannot be null or empty!"));
    }

    /**
     * @{@inheritDoc}
     */
    @Override
    public boolean containsFullName(final String fullName) {
        return CollectionUtil.contains(getMemberSet(), FULL_NAME_COMPARATOR, ParameterUtil.ensureParameter(fullName, "Full name cannot be null or empty!"));
    }

    /**
     * @{@inheritDoc}
     */
    @Override
    public Org getOrg(){
        return org;
    }

}
