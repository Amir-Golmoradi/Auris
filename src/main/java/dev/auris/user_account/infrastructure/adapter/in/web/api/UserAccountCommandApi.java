package dev.auris.user_account.infrastructure.adapter.in.web.api;

import dev.auris.user_account.application.command.change_account_email.ChangeEmailCommand;
import dev.auris.user_account.application.command.change_account_email.ChangeEmailHandler;
import dev.auris.user_account.application.command.change_account_name.ChangeFullNameCommand;
import dev.auris.user_account.application.command.change_account_name.ChangeFullNameHandler;
import dev.auris.user_account.application.command.change_account_password.ChangePasswordCommand;
import dev.auris.user_account.application.command.change_account_password.ChangePasswordHandler;
import dev.auris.user_account.application.command.register_new_owner_account.RegisterNewOwnerHandler;
import dev.auris.user_account.infrastructure.adapter.in.web.dto.mapper.RegisterNewOwnerCommandMapper;
import dev.auris.user_account.infrastructure.adapter.in.web.dto.mapper.RegisterNewOwnerResponseDtoMapper;
import dev.auris.user_account.infrastructure.adapter.in.web.dto.request.ChangeEmailRequest;
import dev.auris.user_account.infrastructure.adapter.in.web.dto.request.ChangeFullNameRequest;
import dev.auris.user_account.infrastructure.adapter.in.web.dto.request.ChangePasswordRequest;
import dev.auris.user_account.infrastructure.adapter.in.web.dto.request.RegisterNewOwnerRequest;
import dev.auris.user_account.infrastructure.adapter.in.web.dto.response.ChangeEmailResponseDto;
import dev.auris.user_account.infrastructure.adapter.in.web.dto.response.ChangeFullNameResponseDto;
import dev.auris.user_account.infrastructure.adapter.in.web.dto.response.ChangePasswordResponseDto;
import dev.auris.user_account.infrastructure.adapter.in.web.dto.response.RegisterNewOwnerResponseDto;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class UserAccountCommandApi {
    private static final Logger log = LoggerFactory.getLogger(UserAccountCommandApi.class);

    private final ChangeEmailHandler changeEmailHandler;
    private final RegisterNewOwnerHandler registerNewOwner;
    private final ChangeFullNameHandler changeFullNameHandler;
    private final ChangePasswordHandler changePasswordHandler;
    private final RegisterNewOwnerCommandMapper commandMapper;
    private final RegisterNewOwnerResponseDtoMapper responseDtoMapper;

    public UserAccountCommandApi(
            RegisterNewOwnerCommandMapper commandMapper,
            RegisterNewOwnerHandler registerNewOwner,
            RegisterNewOwnerResponseDtoMapper responseDtoMapper,
            ChangeEmailHandler changeEmailHandler,
            ChangeFullNameHandler changeFullNameHandler,
            ChangePasswordHandler changePasswordHandler
    ) {
        this.commandMapper = commandMapper;
        this.registerNewOwner = registerNewOwner;
        this.responseDtoMapper = responseDtoMapper;
        this.changeEmailHandler = changeEmailHandler;
        this.changeFullNameHandler = changeFullNameHandler;
        this.changePasswordHandler = changePasswordHandler;
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterNewOwnerResponseDto> createOwner(@Valid @RequestBody RegisterNewOwnerRequest request) {
        log.info("Received register request: {}", request);
        var command = commandMapper.apply(request);
        var registerNewOwnerResponse = registerNewOwner.handle(command);
        var responseDto = responseDtoMapper.apply(registerNewOwnerResponse);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @PatchMapping("/{userAccountId}/email")
    public ResponseEntity<ChangeEmailResponseDto> changeEmail(
            @PathVariable String accountId,
            @Valid @RequestBody ChangeEmailRequest request
    ) {
        log.info("Received change email request for userAccountId={}", accountId);
        var command = new ChangeEmailCommand(accountId, request.updatedEmail());
        var response = changeEmailHandler.handle(command);
        return ResponseEntity.ok(new ChangeEmailResponseDto(
                response.accountId(),
                response.updatedEmail()
        ));
    }

    @PatchMapping("/{userAccountId}/name")
    public ResponseEntity<ChangeFullNameResponseDto> changeFullName(
            @PathVariable String accountId,
            @Valid @RequestBody ChangeFullNameRequest request
    ) {
        log.info("Received change full name request for userAccountId={}", accountId);
        var command = new ChangeFullNameCommand(accountId, request.firstName(), request.lastName());
        var response = changeFullNameHandler.handle(command);
        return ResponseEntity.ok(new ChangeFullNameResponseDto(
                response.accountId(),
                response.firstName(),
                response.lastName()
        ));
    }

    @PatchMapping("/{userAccountId}/password")
    public ResponseEntity<ChangePasswordResponseDto> changePassword(
            @PathVariable String accountId,
            @Valid @RequestBody ChangePasswordRequest request
    ) {
        log.info("Received change password request for userAccountId={}", accountId);
        var command = new ChangePasswordCommand(accountId, request.newPassword());
        var response = changePasswordHandler.handle(command);
        return ResponseEntity.ok(new ChangePasswordResponseDto(response.userAccountId()));
    }
}
