package dev.auris.user_account.infrastructure.adapter.in.web.api;

import dev.auris.user_account.application.query.get_user_account_by_id.GetUserAccountByIdHandler;
import dev.auris.user_account.application.query.get_user_account_by_id.GetUserAccountByIdQuery;
import dev.auris.user_account.application.query.get_user_account_by_username.GetUserAccountByUsernameHandler;
import dev.auris.user_account.application.query.get_user_account_by_username.GetUserAccountByUsernameQuery;
import dev.auris.user_account.application.query.get_user_account_profile.GetUserAccountProfileHandler;
import dev.auris.user_account.application.query.get_user_account_profile.GetUserAccountProfileQuery;
import dev.auris.user_account.application.query.list_user_accounts.ListUserAccountsHandler;
import dev.auris.user_account.application.query.list_user_accounts.ListUserAccountsItem;
import dev.auris.user_account.application.query.list_user_accounts.ListUserAccountsQuery;
import dev.auris.user_account.infrastructure.adapter.in.web.dto.response.GetUserAccountByIdResponseDto;
import dev.auris.user_account.infrastructure.adapter.in.web.dto.response.GetUserAccountByUsernameResponseDto;
import dev.auris.user_account.infrastructure.adapter.in.web.dto.response.GetUserAccountProfileResponseDto;
import dev.auris.user_account.infrastructure.adapter.in.web.dto.response.ListUserAccountsItemDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserAccountQueryApi {
    private final GetUserAccountByIdHandler getUserAccountByIdHandler;
    private final GetUserAccountByUsernameHandler getUserAccountByUsernameHandler;
    private final GetUserAccountProfileHandler getUserAccountProfileHandler;
    private final ListUserAccountsHandler listUserAccountsHandler;

    public UserAccountQueryApi(
            GetUserAccountByIdHandler getUserAccountByIdHandler,
            GetUserAccountByUsernameHandler getUserAccountByUsernameHandler,
            GetUserAccountProfileHandler getUserAccountProfileHandler,
            ListUserAccountsHandler listUserAccountsHandler
    ) {
        this.getUserAccountByIdHandler = getUserAccountByIdHandler;
        this.getUserAccountByUsernameHandler = getUserAccountByUsernameHandler;
        this.getUserAccountProfileHandler = getUserAccountProfileHandler;
        this.listUserAccountsHandler = listUserAccountsHandler;
    }

    @GetMapping("/{userAccountId}")
    public GetUserAccountByIdResponseDto getById(@PathVariable String userAccountId) {
        var response = getUserAccountByIdHandler.handle(new GetUserAccountByIdQuery(userAccountId));
        return new GetUserAccountByIdResponseDto(
                response.userAccountId(),
                response.email(),
                response.username(),
                response.firstName(),
                response.lastName(),
                response.phoneNumber(),
                response.description(),
                response.status(),
                response.createdAt()
        );
    }

    @GetMapping("/by-username")
    public GetUserAccountByUsernameResponseDto getByUsername(@RequestParam String username) {
        var response = getUserAccountByUsernameHandler.handle(new GetUserAccountByUsernameQuery(username));
        return new GetUserAccountByUsernameResponseDto(
                response.userAccountId(),
                response.email(),
                response.username(),
                response.firstName(),
                response.lastName(),
                response.phoneNumber(),
                response.description(),
                response.status(),
                response.createdAt()
        );
    }

    @GetMapping("/{userAccountId}/profile")
    public GetUserAccountProfileResponseDto getProfile(@PathVariable String userAccountId) {
        var response = getUserAccountProfileHandler.handle(new GetUserAccountProfileQuery(userAccountId));
        return new GetUserAccountProfileResponseDto(
                response.userAccountId(),
                response.username(),
                response.email(),
                response.firstName(),
                response.lastName(),
                response.phoneNumber(),
                response.description()
        );
    }

    @GetMapping
    public List<ListUserAccountsItemDto> listAll() {
        return listUserAccountsHandler.handle(new ListUserAccountsQuery()).stream()
                .map(this::toDto)
                .toList();
    }

    private ListUserAccountsItemDto toDto(ListUserAccountsItem item) {
        return new ListUserAccountsItemDto(
                item.userAccountId(),
                item.email(),
                item.username(),
                item.firstName(),
                item.lastName(),
                item.phoneNumber(),
                item.description(),
                item.status(),
                item.createdAt()
        );
    }
}
