package dev.auris.membership.infrastructure.adapter.out.persistence.mapper;

import dev.auris.membership.domain.model.Membership;
import dev.auris.membership.domain.value_object.MembershipId;
import dev.auris.membership.infrastructure.adapter.out.persistence.jpa.model.MembershipJpaEntity;
import dev.auris.organization.domain.value_object.OrganizationId;
import dev.auris.user_account.domain.value_object.UserAccountId;
import org.springframework.stereotype.Service;

@Service
public class MembershipAggregateMapper {

    public Membership toDomain(MembershipJpaEntity entity) {
        return Membership.reconstruct(
                MembershipId.of(entity.getId().toString()),
                UserAccountId.fromString(entity.getUserAccountId().toString()),
                OrganizationId.fromString(entity.getOrganizationId().toString()),
                entity.getRole(),
                entity.getStatus(),
                entity.getJoinedAt(),
                entity.getUpdatedAt()
        );
    }

    public MembershipJpaEntity toEntity(Membership membership) {
        return new MembershipJpaEntity(
                membership.getId().getValue(),
                membership.getUserAccountId().getValue(),
                membership.getOrganizationId().getValue(),
                membership.getRole(),
                membership.getStatus(),
                membership.getJoinedAt(),
                membership.getUpdatedAt()
        );
    }
}
