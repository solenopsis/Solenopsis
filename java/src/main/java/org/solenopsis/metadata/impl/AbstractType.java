package org.solenopsis.metadata.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;
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
    private final String directoryName;
    private final String suffix;
    private final String xmlName;
    private final boolean hasMetaFile;
    private final Org org;

    private final Map<String, Member> memberMap;

    protected Map<String, Member> getMemberMap() {
        return memberMap;
    }

    protected AbstractType(final Org org, final String directoryName, final String suffix, final String xmlName, final boolean hasMetaFile, final Collection<Member> memberCollection) {
        ParameterUtil.ensureParameter(org,           "Org cannot be null!");
        ParameterUtil.ensureParameter(directoryName, "Directory name cannot be null or empty!");
        ParameterUtil.ensureParameter(suffix,        "Suffix cannot be null or empty!");
        ParameterUtil.ensureParameter(xmlName,       "XML name cannot be null or empty!");

        this.org           = org;
        this.directoryName = directoryName;
        this.suffix        = suffix;
        this.xmlName       = xmlName;
        this.hasMetaFile   = hasMetaFile;
        this.memberMap     = new TreeMap<>();

        for(final Member member : memberCollection) {
            this.memberMap.put(member.getFileName(), member.copy(this));
        }
    }

    protected AbstractType(final Org org, final String directoryName, final String suffix, final String xmlName, final boolean hasMetaFile) {
        this(org, directoryName, suffix, xmlName, hasMetaFile, Collections.EMPTY_LIST);
    }

    protected AbstractType(final Org org, Type toCopy) {
        this(org, ParameterUtil.ensureParameter(toCopy, "Type to copy cannot be null!").getDirectoryName(), toCopy.getSuffix(), toCopy.getXmlName(), toCopy.hasMetaFile(), toCopy.getMembers());
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

    /**
     * @{@inheritDoc}
     */
    @Override
    public Collection<Member> getMembers() {
        return Collections.unmodifiableCollection(getMemberMap().values());
    }

    /**
     * @{@inheritDoc}
     */
    @Override
    public Member getByFileName(final String fileName) {
        return getMemberMap().get(fileName);
    }

    /**
     * @{@inheritDoc}
     */
    @Override
    public Member add(final Member member) {
        return getMemberMap().put(ParameterUtil.ensureParameter(member, "Cannot add a null member!").getFileName(), member.copy(this));
    }

    /**
     * @{@inheritDoc}
     */
    @Override
    public boolean containsMember(final String fileName) {
        return getMemberMap().containsKey(ParameterUtil.ensureParameter(fileName, "File name cannot be null or empty!"));
    }

    /**
     * @{@inheritDoc}
     */
    @Override
    public String getDirectoryName() {
        return directoryName;
    }

    /**
     * @{@inheritDoc}
     */
    @Override
    public String getSuffix(){
        return suffix;
    }

    /**
     * @{@inheritDoc}
     */
    @Override
    public String getXmlName(){
        return xmlName;
    }

    /**
     * @{@inheritDoc}
     */
    @Override
    public boolean hasMetaFile(){
        return hasMetaFile;
    }

    /**
     * @{@inheritDoc}
     */
    @Override
    public Org getOrg(){
        return org;
    }

}
