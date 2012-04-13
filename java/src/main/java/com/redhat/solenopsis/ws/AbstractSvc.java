package com.redhat.solenopsis.ws;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.ws.BindingProvider;

/**
 *
 * The purpose of this class is
 *
 * @author sfloess
 *
 */
public abstract class AbstractSvc {
    /**
     * Our logger.
     */
    private final Logger logger;
    
    /**
     * Default constructor.
     */
    AbstractSvc() {
        logger = Logger.getLogger(getClass().getName());
    }

    /**
     * Return the logger.
     */
    protected Logger getLogger() {
        return logger;
    }

    /**
     * Set the URL to call on the web service.
     *
     * @param port is the web service port.
     * @param url is the URL for the call.
     */
    protected void setUrl(final BindingProvider bindingProvider, final String url) {
        bindingProvider.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, url);
        if (getLogger().isLoggable(Level.INFO)) {
            getLogger().log(Level.INFO, "Seting URL to [{0}]", url);
        }
    }

}
