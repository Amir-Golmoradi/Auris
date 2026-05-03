package dev.auris.user_account.infrastructure.adapter.out.persistence.mapper;

import dev.auris.user_account.domain.enums.UserStatus;
import dev.auris.user_account.domain.model.UserAccount;
import dev.auris.user_account.domain.value_object.Email;
import dev.auris.user_account.domain.value_object.FullName;
import dev.auris.user_account.domain.value_object.Password;
import dev.auris.user_account.domain.value_object.PhoneNumber;
import dev.auris.user_account.domain.value_object.UserAccountId;
import dev.auris.user_account.domain.value_object.UserName;
import dev.auris.user_account.infrastructure.adapter.out.persistence.jpa.model.UserAccountJpaEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import static java.util.Objects.requireNonNull;

@Service
public class UserAccountAggregateMapper {

    protected static void requireNonBlank(String s, String message) {
        if (!StringUtils.hasText(s)) throw new IllegalArgumentException(message);
    }

    public UserAccount toDomain(UserAccountJpaEntity entity) {
        requireNonNull(entity, "UserEntity must not be null");

        requireNonBlank(entity.getEmail(), "UserEntity.email is required");
        requireNonBlank(entity.getPassword(), "UserEntity.password is required");
        requireNonBlank(entity.getUsername(), "UserEntity.username is required");
        requireNonBlank(entity.getFirstName(), "UserEntity.firstName is required");
        requireNonBlank(entity.getLastName(), "UserEntity.lastName is required");
        requireNonBlank(entity.getPhoneNumber(), "UserEntity.phoneNumber is required");

        var emailVo = Email.of(entity.getEmail());
        var passwordVo = Password.ofHashed(entity.getPassword());
        var userNameVo = UserName.of(entity.getUsername());
        var fullNameVo = FullName.of(entity.getFirstName(), entity.getLastName());
        var phoneVo = PhoneNumber.of(entity.getPhoneNumber());
        var userId = UserAccountId.fromString(entity.getId().toString());

        return UserAccount.reconstruct(
                userId,
                emailVo,
                passwordVo,
                userNameVo,
                fullNameVo,
                phoneVo,
                entity.getDescription(),
                entity.getCreatedAt(),
                entity.getStatus()
        );
    }

    public UserAccountJpaEntity toEntity(UserAccount userAccount) {
        requireNonNull(userAccount, "User (domain) must not be null");

        requireNonNull(userAccount.getEmail(), "User.email is required");
        requireNonNull(userAccount.getPassword(), "User.password is required");
        requireNonNull(userAccount.getFullName(), "User.fullName is required");
        requireNonNull(userAccount.getPhoneNumber(), "User.phoneNumber is required");
        requireNonNull(userAccount.getUsername(), "User.userName is required");

        var email = userAccount.getEmail().getValue();
        var password = userAccount.getPassword().getValue();
        var username = userAccount.getUsername().getValue();
        var firstName = userAccount.getFullName().getFirstName();
        var lastName = userAccount.getFullName().getLastName();
        var phone = userAccount.getPhoneNumber().getValue();
        var description = userAccount.getDescription();
        var status = userAccount.getStatus() == null ? UserStatus.ACTIVE : userAccount.getStatus();

        return UserAccountJpaEntity.builder()
                .id(userAccount.getId() == null ? null : userAccount.getId().getValue())
                .createdAt(userAccount.getCreatedAt())
                .username(username)
                .email(email)
                .password(password)
                .firstName(firstName)
                .lastName(lastName)
                .phoneNumber(phone)
                .description(description)
                .status(status)
                .build();
    }
}
