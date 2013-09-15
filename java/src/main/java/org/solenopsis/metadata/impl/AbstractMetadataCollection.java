package org.solenopsis.metadata.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import org.flossware.util.CollectionUtil;
import org.flossware.util.ObjectFilter;
import org.flossware.util.ParameterUtil;
import org.solenopsis.metadata.MetadataCollection;
import org.solenopsis.metadata.MetadataCollectable;

/**
 * Abstract base class of collections.
 *
 * @param <T> the type in the collection.
 *
 * @author sfloess
 */
public abstract class AbstractMetadataCollection<T extends MetadataCollectable> extends AbstractMetadata implements MetadataCollection<T> {
    /**
     * The internal collection.
     */
    private final Set<T> set;

    /**
     * Return the internal collection.
     *
     * @return the internal collection.
     */
    protected Set<T> getSet() {
        return set;
    }

    /**
     * Default constructor.
     */
    protected AbstractMetadataCollection() {
        this.set = new HashSet<>();
    }

    /**
     * @{@inheritDoc}
     */
    @Override
    public void toString(final StringBuilder stringBuilder, final String prefix) {
        stringBuilder.append(prefix).append("Children(").append(getSet().size()).append("):").append(LINE_SEPARATOR_STRING);

        final String memberPrefix = prefix + "    ";

        for (final T val : getSet()) {
            val.toString(stringBuilder, memberPrefix);
        }
    }

    /**
     * @{@inheritDoc}
     */
    @Override
    public Collection<T> getCollection() {
        return Collections.unmodifiableCollection(getSet());
    }

    /**
     * @{@inheritDoc}
     */
    @Override
    public Collection<T> getSortedCollection(final Comparator<T> comparator) {
        return CollectionUtil.sort(getSet(), comparator);
    }

    /**
     * @{@inheritDoc}
     */
    @Override
    public T add(final T entity) {
        if (getSet().contains(ParameterUtil.ensureParameter(entity, "Cannot add a null entity"))) {
            return entity;
        }

        MetadataCollectable m = entity.copy(this);

        getSet().add(entity.copy(m));

        return retVal;
    }

    /**
     * @{@inheritDoc}
     */
    @Override
    public <V> T findForValue(final ObjectFilter<T, V> filter, final V value, final T defaultIfNotFound) {
        return CollectionUtil.find(getSet(), filter, value, defaultIfNotFound);
    }

    /**
     * @{@inheritDoc}
     */
    @Override
    public <V> T findForValue(final ObjectFilter<T, V> filter, final V value) {
        return CollectionUtil.find(getSet(), filter, ParameterUtil.ensureParameter(value, "Value to find cannot be null"));
    }

    /**
     * @{@inheritDoc}
     */
    @Override
    public <V> boolean containsValue(final ObjectFilter<T, V> filter, final V value) {
        return CollectionUtil.contains(getSet(), filter, value);
    }
}
