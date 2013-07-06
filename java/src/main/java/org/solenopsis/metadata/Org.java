package org.solenopsis.metadata;

import java.util.List;
import org.flossware.util.Stringifiable;

/**
 *
 * Represents an org's metadata
 *
 * @author sfloess
 *
 */
public interface Org extends Stringifiable {
    List<Type> getMetadata();
}
