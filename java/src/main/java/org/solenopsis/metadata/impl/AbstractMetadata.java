package org.solenopsis.metadata.impl;

import java.util.logging.Logger;
import org.solenopsis.metadata.Metadata;

/**
 *
 * Default base class for metadata.
 *
 * @author sfloess
 *
 */
public abstract class AbstractMetadata implements Metadata {
    public static final String LINE_SEPARATOR_PROPERTY = "line.separator";
    public static final String LINE_SEPARATOR_STRING = System.getProperty(LINE_SEPARATOR_PROPERTY);

    private final Logger logger;

    protected Logger getLogger() {
        return logger;
    }

    protected AbstractMetadata() {
        this.logger = Logger.getLogger(getClass().getName());
    }

    /**
     * @{@inheritDoc}
     */
    @Override
    public void toString(final StringBuilder stringBuilder) {
        toString(stringBuilder, "");
    }

    /**
     * @{@inheritDoc}
     */
    public String toString(final String prefix) {
        final StringBuilder sb = new StringBuilder();

        toString(sb, prefix);

        return sb.toString();
    }

    /**
     * @{@inheritDoc}
     */
    @Override
    public String toString() {
        return toString("");
    }
}
