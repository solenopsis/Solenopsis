package org.solenopsis.ws.query;

//import com.redhat.gss.jacinto.sfdc.ws.EnterpriseWebService;
import org.solenopsis.sforce.soap.enterprise.QueryResult;
import org.solenopsis.sforce.soap.enterprise.SObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
//import org.jboss.logging.Logger;

/**
 *
 * A wrapper around using the enterprise web service for querying.  This class
 * is NOT thread safe.
 *
 * @author sfloess
 *
 */
public class EnterpriseWebServiceQueryMgr implements QueryMgr {
    /**
     * Helper method to construct a map to store query ids to query results.
     * 
     * @return a map to store query ids to query results.
     */
    private static Map<QueryId, QueryResult> createQueryResultMap() {
        return new HashMap<QueryId, QueryResult>();
    }
    
    /**
     * Used for logging.
     */
    //private final Logger logger;   
    
    /**
     * Holds all query results.
     */
    private final Map<QueryId, QueryResult> queryResultMap;
    
    /**
     * Return our logger.
     *
     * @return our logger.
     */
    //protected Logger getLogger() {
    //    return logger;
    //}
    
    /**
     * Return the query result map.
     * 
     * @return the query result map.
     */
    protected Map<QueryId, QueryResult> getQueryResultMap() {
        return queryResultMap;
    }

    /**
     * Default constructor.
     */
    public EnterpriseWebServiceQueryMgr() {
        //this.logger = Logger.getLogger(getClass());
        this.queryResultMap = createQueryResultMap();
    }
    
    /**
     * {@inheritDoc}
     */    
    @Override
    public QueryId query(final String queryStr) throws Exception {
        final DefaultQueryId retVal = new DefaultQueryId();
        
        /*
        if (getLogger().isDebugEnabled()) {
            getLogger().debug("Querying id [" + retVal + "] for query [" + queryStr + "]");
        }

        */
        
        final QueryResult queryResult = null;//EnterpriseWebService.getInstance().getPort().query(queryStr);
        
        /*
        if (getLogger().isDebugEnabled()) {
            getLogger().debug("Created query using id [" + retVal +"].  Total records [" + queryResult.getSize() + "] for query [" + queryStr + "]");
        }
        * 
        */
        
        if (!queryResult.getRecords().isEmpty()) {
            getQueryResultMap().put(retVal, queryResult);
        } else {
            /*
            if (getLogger().isDebugEnabled()) {
                getLogger().debug("No data in query for id [" + retVal + " - not caching query result!");
            }
            
            */
        }
        
        return retVal;
    }
 
    /**
     * {@inheritDoc}
     */    
    @Override
    public void close(final QueryId id) {
        /*
        if (getLogger().isDebugEnabled()) {
            getLogger().debug("Removing query id [" + id + "]");
        }
        * 
        */
        
        getQueryResultMap().remove(id);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override    
    public boolean hasNext(final QueryId id) {
        final QueryResult queryResult = getQueryResultMap().get(id);
        
        return (queryResult != null);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public List<SObject> next(QueryId id) throws Exception {
        final QueryResult queryResult = getQueryResultMap().get(id);
        
        if (queryResult == null) {
            return null;
        }
        
        final List<SObject> retVal = queryResult.getRecords();
        
        if (!queryResult.isDone()) {
            /*
            if (getLogger().isDebugEnabled()) {
                getLogger().debug("Grabbing next batch for id [" + id + "]");
            }
            * 
            */
            
            //getQueryResultMap().put(id, EnterpriseWebService.getInstance().getPort().queryMore(queryResult.getQueryLocator()));
        } else {
            close (id);
        }
        
        return retVal;
    }
}
