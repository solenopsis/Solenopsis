package org.solenopsis.metadata.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import org.flossware.util.CollectionUtil;
import org.flossware.util.ObjectFilter;
import org.solenopsis.metadata.Metadata;
import org.solenopsis.metadata.MetadataCollection;

/**
 * Abstract base class of collections.
 *
 * @param <T> the type in the collection.
 *
 * @author sfloess
 */
public abstract class AbstractMetadataCollection<T extends Metadata> extends AbstractMetadata implements MetadataCollection<T> {
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
     * Return the internal collection sorted.
     *
     * @param filter used to filter.
     *
     * @return the internal collection sorted.
     */
    protected Collection<T> getSortedCollection(final Comparator<T> comparator) {
        return CollectionUtil.sort(getSet(), comparator);
    }

    /**
     * Using filter, find a value and return the member who meets the filter or return
     * <code>defaultIfNotFound</code> if value is not found.
     *
     * @param <V> value sought.
     * @param filter will filter out objects.
     * @param value value sought.
     * @param defaultIfNotFound the value to return if <code>value</code> is not found.
     *
     * @return the object from the collection that has the value.
     */
    protected <V> T findForValue(final ObjectFilter<T, V> filter, final V value, final T defaultIfNotFound) {
        return CollectionUtil.find(getSet(), filter, value, defaultIfNotFound);
    }

    /**
     * Using filter, find a value and return the member who meets the filter.
     *
     * @param <V> value sought.
     * @param filter will filter out objects.
     * @param value value sought.
     *
     * @return the object from the collection that has the value.
     */
    protected <V> T findForValue(final ObjectFilter<T, V> filter, final V value) {
        return CollectionUtil.find(getSet(), filter, value);
    }

    /**
     * Return true if the collection contains value or false if not.
     *
     * @param <V> value sought.
     * @param filter will filter out objects.
     * @param value value sought.
     *
     * @return true if the collection contains value or false if not.
     */
    protected <V> boolean containsValue(final ObjectFilter<T, V> filter, final V value) {
        return CollectionUtil.contains(getSet(), filter, value);
    }
}
