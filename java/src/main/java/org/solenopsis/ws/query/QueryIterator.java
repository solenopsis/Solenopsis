package org.solenopsis.ws.query;

/**
 *
 * This class queries cases.
 *
 * @author sfloess
 *
 */
public interface QueryIterator<V> {
    /**
     * Return true if there is more data or false if not.
     * 
     * @return true if there is more data or false if not.
     * 
     * @throws Exception if computing if there are more records has a problem.
     */
    public boolean hasNext() throws Exception;

    /**
     * Return the next object.
     * 
     * @return the next object. 
     */
    public V next() ;
}
