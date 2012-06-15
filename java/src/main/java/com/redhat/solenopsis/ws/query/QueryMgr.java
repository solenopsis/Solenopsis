package com.redhat.solenopsis.ws.query;

import com.redhat.sforce.soap.enterprise.SObject;
import java.util.List;

/**
 *
 * This interface is used to abstract out querying from SFDC.  We need this so that
 * we can test the various indexers without needing to hit SFDC directly.
 *
 * @author sfloess
 *
 */
public interface QueryMgr {
    /**
     * Initiates a query returning an identifier so that one can get the results
     * of the data returned from <code>query</code>.
     * 
     * @param query A raw SOQL query.
     * 
     * @return an identifier that can be used to get the results of the query using
     *         <code>getBatch()</code>.
     * 
     * @throws Exception if any problems arise issue the query. 
     */
    public QueryId query(final String query) throws Exception;
    
    /**
     * This denotes we are done processing this query.  Use this method if you
     * wish to stop processing data early.
     * 
     * @param id the unique identifier created when the <code>query()</code>
     *        was called.
     */
    public void close(QueryId id);
    
    /**
     * Returns true if there are more batches to process or false if not.
     * 
     * @param id the unique identifier to determine if there are more batches to
     *        process.
     * 
     * @return true if there are more objects to process or false if not.
     */
    public boolean hasNext(QueryId id);
    
    /**
     * Return the next batch of objects from the query.
     * 
     * @param id a unique identifier that represents the query.
     * 
     * @return the next batch of objects from the query or null if there are no
     *         more objects to process.
     * 
     * @throws Exception if any problems arise getting the next batch of objects
     *         to process.
     */
    public List<SObject> next(QueryId id) throws Exception;
}
