package org.solenopsis.metadata;

import java.util.Collection;
import java.util.Comparator;
import org.flossware.util.ObjectFilter;

/**
 *
 * The purpose of this interface is to define the API for a metadata collection.
 *
 * @author sfloess
 *
 */
public interface MetadataCollection<T extends MetadataCollectable> extends Metadata {
    /**
     * Return the internal collection.
     *
     * @return the internal collection.
     */
    Collection<T> getCollection();

    /**
     * Return the collection sorted.
     *
     * @param filter used to filter.
     *
     * @return the internal collection sorted.
     */
    Collection<T> getSortedCollection(Comparator<T> comparator);

    /**
     * Add to the collection.
     *
     * @param entity is the object to add to the collection.
     *
     * @return the added entity.
     */
    T add(T entity);

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
    <V> T findForValue(ObjectFilter<T, V> filter, V value, T defaultIfNotFound);

    /**
     * Using filter, find a value and return the member who meets the filter.
     *
     * @param <V> value sought.
     * @param filter will filter out objects.
     * @param value value sought.
     *
     * @return the object from the collection that has the value.
     */
    <V> T findForValue(ObjectFilter<T, V> filter, V value);

    /**
     * Return true if the collection contains value or false if not.
     *
     * @param <V> value sought.
     * @param filter will filter out objects.
     * @param value value sought.
     *
     * @return true if the collection contains value or false if not.
     */
    <V> boolean containsValue(ObjectFilter<T, V> filter, V value);
}
