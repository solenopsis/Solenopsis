package org.solenopsis.metadata.util;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import org.solenopsis.metadata.Member;
import org.solenopsis.metadata.Org;

/**
 *
 * Represents the differences in orgs.
 *
 * @author sfloess
 *
 */
public class Diff {
    protected static Collection<Member> computeMissing(final Org src, final Org dest) {
        final List<Member> retVal = new LinkedList<Member>();

        for(final Member srcMember : src.getAllMembers()) {
            if(!dest.containsMember(srcMember.getFileName())) {
                retVal.add(srcMember);
            }
        }

        return Collections.unmodifiableCollection(retVal);
    }

    public static Collection<Member> computeAdded(final Org src, final Org dest) {
        return computeMissing(dest, src);
    }

    public static Collection<Member> computeRemoved(final Org src, final Org dest) {
        return computeMissing(src, dest);
    }
}
