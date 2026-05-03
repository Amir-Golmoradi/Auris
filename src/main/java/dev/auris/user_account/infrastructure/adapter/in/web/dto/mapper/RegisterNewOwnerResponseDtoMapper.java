package dev.auris.user_account.infrastructure.adapter.in.web.dto.mapper;

import dev.auris.user_account.application.command.register_new_owner_account.RegisterNewOwnerResponse;
import dev.auris.user_account.infrastructure.adapter.in.web.dto.response.RegisterNewOwnerResponseDto;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class RegisterNewOwnerResponseDtoMapper
        implements Function<RegisterNewOwnerResponse, RegisterNewOwnerResponseDto> {

    @Override
    public RegisterNewOwnerResponseDto apply(RegisterNewOwnerResponse response) {
        return new RegisterNewOwnerResponseDto(
                response.accountId(),
                response.organizationId(),
                response.email(),
                response.username(),
                response.firstName(),
                response.lastName(),
                response.phoneNumber(),
                response.organizationName(),
                response.officePhoneNumber(),
                response.description()
        );
    }
}
