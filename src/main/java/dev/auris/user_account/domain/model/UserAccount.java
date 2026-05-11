package dev.auris.user_account.domain.model;

import dev.auris.shared.building_block.AggregateRoot;
import dev.auris.shared.building_block.DomainEvent;
import dev.auris.user_account.domain.enums.UserStatus;
import dev.auris.user_account.domain.event.EmailChangedEvent;
import dev.auris.user_account.domain.event.FullNameChangedEvent;
import dev.auris.user_account.domain.event.PasswordChangedEvent;
import dev.auris.user_account.domain.event.PhoneNumberChangedEvent;
import dev.auris.user_account.domain.exception.UserAccountStateException;
import dev.auris.user_account.domain.value_object.Email;
import dev.auris.user_account.domain.value_object.FullName;
import dev.auris.user_account.domain.value_object.Password;
import dev.auris.user_account.domain.value_object.PhoneNumber;
import dev.auris.user_account.domain.value_object.UserAccountId;
import dev.auris.user_account.domain.value_object.UserName;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Objects;

public class UserAccount extends AggregateRoot<UserAccountId, DomainEvent> {
    private final UserName username;
    private final OffsetDateTime createdAt;
    private String description;
    private Email email;
    private Password password;
    private FullName fullName;
    private PhoneNumber phoneNumber;
    private UserStatus status;

    private UserAccount(
            UserAccountId accountId,
            Email email,
            UserName username,
            FullName fullName,
            PhoneNumber phoneNumber,
            String description,
            OffsetDateTime createdAt,
            UserStatus status
    ) {
        super(accountId);
        this.email = Objects.requireNonNull(email, "Email is required");
        this.username = Objects.requireNonNull(username, "Username is required");
        this.fullName = Objects.requireNonNull(fullName, "Full Name is required");
        this.phoneNumber = Objects.requireNonNull(phoneNumber, "Phone Number is required");
        this.description = description;
        this.createdAt = Objects.requireNonNull(createdAt, "CreatedAt is required");
        this.status = Objects.requireNonNull(status, "Status is required");
    }

    private UserAccount(
            UserAccountId accountId,
            Email email,
            Password password,
            UserName username,
            FullName fullName,
            PhoneNumber phoneNumber,
            String description,
            OffsetDateTime createdAt,
            UserStatus status
    ) {
        super(accountId);
        this.email = Objects.requireNonNull(email, "Email is required");
        this.password = Objects.requireNonNull(password, "Password is required");
        this.username = Objects.requireNonNull(username, "Username is required");
        this.fullName = Objects.requireNonNull(fullName, "Full Name is required");
        this.phoneNumber = Objects.requireNonNull(phoneNumber, "Phone Number is required");
        this.description = description;
        this.createdAt = Objects.requireNonNull(createdAt, "CreatedAt is required");
        this.status = Objects.requireNonNull(status, "Status is required");
    }

    public static UserAccount create(
            Email email,
            Password password,
            UserName userName,
            FullName fullName,
            PhoneNumber phoneNumber
    ) {
        return new UserAccount(
                UserAccountId.generate(),
                email,
                password,
                userName,
                fullName,
                phoneNumber,
                "",
                OffsetDateTime.now(ZoneOffset.UTC),
                UserStatus.ACTIVE
        );
    }

    public static UserAccount create(
            Email email,
            UserName userName,
            FullName fullName,
            PhoneNumber phoneNumber
    ) {
        return new UserAccount(
                UserAccountId.generate(),
                email,
                userName,
                fullName,
                phoneNumber,
                "",
                OffsetDateTime.now(ZoneOffset.UTC),
                UserStatus.ACTIVE
        );
    }

    public static UserAccount reconstruct(
            UserAccountId userAccountId,
            Email email,
            Password password,
            UserName userName,
            FullName fullName,
            PhoneNumber phoneNumber,
            String description,
            OffsetDateTime createdAt,
            UserStatus status
    ) {
        return new UserAccount(
                userAccountId,
                email,
                password,
                userName,
                fullName,
                phoneNumber,
                description,
                createdAt,
                status
        );
    }

    public static UserAccount registerNewOwner(
            Email email,
            Password password,
            UserName userName,
            FullName fullName,
            PhoneNumber phoneNumber,
            String description
    ) {
        return new UserAccount(
                UserAccountId.generate(),
                email,
                password,
                userName,
                fullName,
                phoneNumber,
                description,
                OffsetDateTime.now(ZoneOffset.UTC),
                UserStatus.ACTIVE
        );
    }

    public Email getEmail() {
        return email;
    }

    public UserStatus getStatus() {
        return status;
    }

    public UserName getUsername() {
        return username;
    }

    public Password getPassword() {
        return password;
    }

    public FullName getFullName() {
        return fullName;
    }

    public UserAccountId getId() {
        return super.getId();
    }

    public String getDescription() {
        return description;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public PhoneNumber getPhoneNumber() {
        return phoneNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        UserAccount userAccount = (UserAccount) o;
        return Objects.equals(username, userAccount.username)
                && Objects.equals(description, userAccount.description)
                && Objects.equals(email, userAccount.email)
                && Objects.equals(password, userAccount.password)
                && Objects.equals(fullName, userAccount.fullName)
                && Objects.equals(phoneNumber, userAccount.phoneNumber)
                && status == userAccount.status;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + Objects.hashCode(username);
        result = 31 * result + Objects.hashCode(description);
        result = 31 * result + Objects.hashCode(email);
        result = 31 * result + Objects.hashCode(password);
        result = 31 * result + Objects.hashCode(fullName);
        result = 31 * result + Objects.hashCode(phoneNumber);
        result = 31 * result + Objects.hashCode(status);
        return result;
    }

    public void changeFullName(FullName newName) {
        ensureIsActive();
        this.fullName = Objects.requireNonNull(newName);
        var event = new FullNameChangedEvent(getId(), newName, OffsetDateTime.now(ZoneOffset.UTC));
        addDomainEvent(event);
    }

    public void changeEmail(Email newEmail) {
        ensureIsActive();
        this.email = Objects.requireNonNull(newEmail);
        var event = new EmailChangedEvent(getId(), newEmail);
        addDomainEvent(event);
    }

    public void changePhoneNumber(PhoneNumber updatedPhoneNumber) {
        ensureIsActive();
        this.phoneNumber = Objects.requireNonNull(updatedPhoneNumber);
        var event = new PhoneNumberChangedEvent(getId(), updatedPhoneNumber);
        addDomainEvent(event);
    }

    public void changePassword(Password newPassword) {
        ensureIsActive();
        this.password = Objects.requireNonNull(newPassword);
        var event = new PasswordChangedEvent(getId());
        addDomainEvent(event);
    }

    public void updateDescription(String description) {
        this.description = description;
    }

    public void activate() {
        ensureIsNotActive();
        this.status = UserStatus.ACTIVE;
    }

    public void deactivate() {
        ensureIsActive();
        this.status = UserStatus.NOT_ACTIVE;
    }

    private void ensureIsActive() {
        if (this.status != UserStatus.ACTIVE) {
            throw UserAccountStateException.notActive(currentUserAccountId());
        }
    }

    private void ensureIsNotActive() {
        if (this.status != UserStatus.NOT_ACTIVE) {
            throw UserAccountStateException.alreadyActive(currentUserAccountId());
        }
    }

    private String currentUserAccountId() {
        return getId() == null ? "unknown" : String.valueOf(getId().getValue());
    }
}
