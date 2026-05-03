package dev.auris.membership.domain.port.out;

import dev.auris.membership.domain.model.Membership;

import java.util.List;

public interface SaveMembershipPort {
    Membership save(Membership membership);
    void saveAll(List<Membership> memberships);
}
