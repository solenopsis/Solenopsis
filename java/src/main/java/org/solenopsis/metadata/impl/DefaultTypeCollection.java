package org.solenopsis.metadata.impl;

import java.util.Collection;
import org.flossware.util.CollectionUtil;
import org.flossware.util.ParameterUtil;
import org.solenopsis.metadata.Member;
import org.solenopsis.metadata.Type;
import org.solenopsis.metadata.TypeCollection;
import static org.solenopsis.metadata.TypeCollection.XML_NAME_COMPARATOR;

/**
 *
 * Abstract base class for org metadata.
 *
 * @author sfloess
 *
 */
public abstract class DefaultTypeCollection extends AbstractMetadataCollection<Type> implements TypeCollection {
    /**
     * Default constructor.
     */
    public DefaultTypeCollection() {
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
        return findForValue(XML_NAME_COMPARATOR, ParameterUtil.ensureParameter(xmlName, "XML name cannot be null or empty"));
    }

    /**
     * @{@inheritDoc}
     */
    @Override
    public Type getByDirName(final String dirName) {
        return findForValue(DIRECTORY_NAME_COMPARATOR, ParameterUtil.ensureParameter(dirName, "Dir name cannot be null or empty"));
    }

    /**
     * @{@inheritDoc}
     */
    @Override
    public void addType(final Type type) {
        if (getSet().contains(ParameterUtil.ensureParameter(type, "Cannot have a null type!"))) {
            return;
        }

        getSet().add(type);
    }
}
