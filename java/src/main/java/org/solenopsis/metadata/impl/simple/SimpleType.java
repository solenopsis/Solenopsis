package org.solenopsis.metadata.impl.simple;

import org.solenopsis.metadata.impl.*;
import org.flossware.util.ParameterUtil;
import org.solenopsis.metadata.Org;
import org.solenopsis.metadata.Type;

/**
 *
 * Abstract base class for root metadata.
 *
 * @author sfloess
 *
 */
public abstract class SimpleType extends AbstractType {
    private final String directoryName;
    private final String suffix;
    private final String xmlName;
    private final boolean hasMetaFile;

    protected SimpleType(final Org org, final String directoryName, final String suffix, final String xmlName, final boolean hasMetaFile) {
        super(org);

        this.directoryName = ParameterUtil.ensureParameter(directoryName, "Directory name cannot be null or empty!");
        this.suffix        = ParameterUtil.ensureParameter(suffix,        "Suffix cannot be null or empty!");
        this.xmlName       = ParameterUtil.ensureParameter(xmlName,       "XML name cannot be null or empty!");
        this.hasMetaFile   = hasMetaFile;
    }

    protected SimpleType(final Org org, Type toCopy) {
        super(org, toCopy);

        this.directoryName = ParameterUtil.ensureParameter(toCopy.getDirectoryName(), "Directory name cannot be null or empty!");
        this.suffix        = ParameterUtil.ensureParameter(toCopy.getSuffix(),        "Suffix cannot be null or empty!");
        this.xmlName       = ParameterUtil.ensureParameter(toCopy.getXmlName(),       "XML name cannot be null or empty!");
        this.hasMetaFile   = toCopy.hasMetaFile();
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
}
