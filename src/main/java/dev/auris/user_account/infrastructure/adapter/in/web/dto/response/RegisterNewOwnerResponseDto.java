package dev.auris.user_account.infrastructure.adapter.in.web.dto.response;

public record RegisterNewOwnerResponseDto(
        String accountId,
        String organizationId,
        String email,
        String username,
        String firstName,
        String lastName,
        String phoneNumber,
        String organizationName,
        String officePhoneNumber,
        String description
) {
    public static RegisterNewOwnerResponseDto create(
            String accountId,
            String organizationId,
            String email,
            String username,
            String firstName,
            String lastName,
            String phoneNumber,
            String organizationName,
            String officePhoneNumber
    ) {

        return new RegisterNewOwnerResponseDto(
                accountId,
                organizationId,
                email,
                username,
                firstName,
                lastName,
                phoneNumber,
                organizationName,
                officePhoneNumber,
                ""
        );
    }
}
