package com.redhat.solenopsis.ws.query;

/**
 * Simple implementation of a query Id.  We're just using current time
 * to represent an id.
 */
public final class DefaultQueryId implements QueryId {
    /**
     * Will be current time.
     */
    private final long id;

    /**
     * Pre-compute the hash code.
     */
    private final int hashCode;

    /**
     * Pre-compute to string...
     */
    private String toString;

    /**
     * Default constructor.
     */
    public DefaultQueryId() {
        id = System.currentTimeMillis();

        final int hash = 3;
        hashCode = 41 * hash + (int) (this.id ^ (this.id >>> 32));

        toString = "" + id;
    }

    /**
     * {@inheritDoc}
     */         
    @Override
    public boolean equals(final Object toCompare) {
        if (null == toCompare || ! (toCompare instanceof DefaultQueryId)) {
            return false;
        }

        return id == ((DefaultQueryId) toCompare).id;
    }

    /**
     * {@inheritDoc}
     */        
    @Override
    public int hashCode() {
        return hashCode;
    }


    /**
     * {@inheritDoc}
     */        
    @Override
    public String toString() {
        return toString;
    }
}
    
