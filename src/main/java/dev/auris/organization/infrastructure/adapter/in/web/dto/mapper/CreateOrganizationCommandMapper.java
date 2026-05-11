package dev.auris.organization.infrastructure.adapter.in.web.dto.mapper;

import dev.auris.organization.application.command.create_organization.CreateOrganizationCommand;
import dev.auris.organization.infrastructure.adapter.in.web.dto.request.CreateOrganizationRequest;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class CreateOrganizationCommandMapper implements Function<CreateOrganizationRequest, CreateOrganizationCommand> {
    @Override
    public CreateOrganizationCommand apply(CreateOrganizationRequest request) {
        return new CreateOrganizationCommand(
                request.name(),
                request.officePhoneNumber()
        );
    }
}
