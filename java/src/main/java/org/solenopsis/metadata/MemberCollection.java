package org.solenopsis.metadata;

import java.util.Collection;
import org.flossware.util.Stringifiable;

/**
 *
 * The purpose of this interface is
 *
 * @author sfloess
 *
 */
public interface MemberCollection extends Stringifiable {
    Collection<Member> getMembers();

    Member getByFileName(String fileName);

    Member add(Member member);

    boolean containsMember(String fileName);
}
