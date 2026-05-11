package dev.auris.user_account.infrastructure.adapter.in.web.dto.mapper;

import dev.auris.user_account.application.command.register_new_owner_account.RegisterNewOwnerCommand;
import dev.auris.user_account.infrastructure.adapter.in.web.dto.request.RegisterNewOwnerRequest;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class RegisterNewOwnerCommandMapper implements Function<RegisterNewOwnerRequest, RegisterNewOwnerCommand> {
    @Override
    public RegisterNewOwnerCommand apply(RegisterNewOwnerRequest request) {
        return new RegisterNewOwnerCommand(
                request.email(),
                request.username(),
                request.password(),
                request.firstName(),
                request.lastName(),
                request.phoneNumber(),
                request.description(),
                request.organizationName(),
                request.officePhoneNumber()
        );
    }
}
