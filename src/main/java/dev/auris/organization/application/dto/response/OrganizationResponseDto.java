package dev.auris.organization.application.dto.response;

import java.time.OffsetDateTime;

public record OrganizationResponseDto(
        String name,
        String officePhoneNumber,
        String invitationCode,
        OffsetDateTime createdAt
) {
    public static OrganizationResponseDto create(String name, String officePhoneNumber, String invitationCode) {
        return new OrganizationResponseDto(name, officePhoneNumber, invitationCode, OffsetDateTime.now());
    }
}
