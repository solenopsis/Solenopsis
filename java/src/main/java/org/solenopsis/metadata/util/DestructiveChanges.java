package org.solenopsis.metadata.util;

import java.util.Collection;
import org.solenopsis.metadata.Member;
import org.solenopsis.metadata.Org;

/**
 *
 * Computes destructive changes.
 *
 * @author sfloess
 *
 */
public class DestructiveChanges {
    public static String computeDestructiveChanges(final Collection<Member> memberCollection) {
        final StringBuilder sb = new StringBuilder();

        return sb.toString();
    }

    public static String computeDestructiveChanges(final Org<?, ?> src, final Org<?, ?> dest) {
        return computeDestructiveChanges(Diff.computeRemoved(src, dest));
    }
}
