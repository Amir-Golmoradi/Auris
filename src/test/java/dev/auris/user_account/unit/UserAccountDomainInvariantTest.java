package dev.auris.user_account.unit;

import dev.auris.organization.domain.service.PasswordHasher;
import dev.auris.user_account.domain.event.EmailChangedEvent;
import dev.auris.user_account.domain.event.FullNameChangedEvent;
import dev.auris.user_account.domain.event.PasswordChangedEvent;
import dev.auris.user_account.domain.event.PhoneNumberChangedEvent;
import dev.auris.user_account.domain.model.UserAccount;
import dev.auris.user_account.domain.value_object.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

public class UserAccountDomainInvariantTest {
    @Test
    @DisplayName("Changing FullName to a new value updates state and emits FullNameChangedEvent")
    public void shouldUpdateFullNameAndEmitFullNameChangedEvent_whenFullNameIsChanged() {
        //1. Create an object for testing it.
        var userAccount = UserAccount.create(
                Email.of("test@example.com"),
                UserName.of("Captain America"),
                FullName.of("John", "Doe"),
                PhoneNumber.of("+19123456789")
        );
        //2. Writing the CORE LOGIC that we want to test.
        FullName updatedFullName = FullName.of("Steve", "Rogers");
        userAccount.changeFullName(updatedFullName);

        //3. Writing the rules for testing the CORE LOGIC.
        assertEquals("Steve", userAccount.getFullName().getFirstName());
        assertEquals("Rogers", userAccount.getFullName().getLastName());
        assertEquals(1, userAccount.getDomainEvents().size());

        FullNameChangedEvent event = assertInstanceOf(
                FullNameChangedEvent.class,
                userAccount.getDomainEvents().getFirst()
        );

        assertEquals(userAccount.getId(), event.userAccountId());
        assertEquals(updatedFullName, event.updatedFullName());
    }

    @Test
    @DisplayName("Changing Email to a new value updates state and emits EmailChangedEvent")
    public void shouldUpdateEmailAndEmitEmailChangedEvent_whenEmailIsChanged() {
        // 1. We create an object to test.
        var userAccount = UserAccount.create(
                Email.of("elonmusk@twitter.com"),
                UserName.of("Mr Incredible"),
                FullName.of("Elon", "Musk"),
                PhoneNumber.of("+989123456789")
        );
        var updatedEmail = Email.of("timcook@apple.com");
        userAccount.changeEmail(updatedEmail);
        assertEquals("timcook@apple.com", userAccount.getEmail().getValue());

        EmailChangedEvent event = assertInstanceOf(
                EmailChangedEvent.class,
                userAccount.getDomainEvents().getFirst()
        );
        assertEquals(userAccount.getId(), event.userAccountId());
        assertEquals(updatedEmail, event.updatedEmail());
    }

    @Test
    @DisplayName("Changing Password stores hashed value and emits PasswordChangedEvent")
    public void shouldStoreHashedPasswordAndEmitPasswordChangedEvent_whenPasswordIsChanged() {
        PasswordHasher hasher = new PasswordHasher() {
            @Override
            public String hash(String rawPassword) {
                return "hashed-" + rawPassword;
            }

            @Override
            public boolean matches(String rawPassword, String hashedPassword) {
                return hashedPassword.equals("hashed-" + rawPassword);
            }
        };
        var user = UserAccount.create(
                Email.of("test@example.com"),
                Password.of("Secret123", hasher),
                UserName.of("Elon Musk"),
                FullName.of("Amir", "Golmoradi"),
                PhoneNumber.of("+989123456789")
        );
        var updatedPassword = Password.of("Strong123", hasher);
        user.changePassword(updatedPassword);
        assertEquals(1, user.getDomainEvents().size());
        assertEquals("hashed-Strong123", user.getPassword().getValue());

        PasswordChangedEvent event = assertInstanceOf(
                PasswordChangedEvent.class,
                user.getDomainEvents().getFirst()
        );
        assertEquals(user.getId().getValue(), event.userAccountId().getValue());
        assertEquals(updatedPassword, user.getPassword());
    }

    @Test
    @DisplayName("Changing PhoneNumber to a new value updates state and emits PhoneNumberChangedEvent")
    public void shouldUpdatePhoneNumberAndEmitPhoneNumberChangedEvent_whenPhoneNumberIsChanged() {
        var userAccount = UserAccount.create(
                Email.of("test@example.com"),
                UserName.of("Captain America"),
                FullName.of("John", "Doe"),
                PhoneNumber.of("+989123456789")
        );

        var updatedPhoneNumber = PhoneNumber.of("+449123456789");
        userAccount.changePhoneNumber(updatedPhoneNumber);
        assertEquals(1, userAccount.getDomainEvents().size());
        assertEquals(updatedPhoneNumber.getValue(), userAccount.getPhoneNumber().getValue());

        PhoneNumberChangedEvent event = assertInstanceOf(
                PhoneNumberChangedEvent.class,
                userAccount.getDomainEvents().getFirst()
        );
        assertEquals(userAccount.getId(), event.userAccountId());
        assertEquals(updatedPhoneNumber.getValue(), event.updatedPhoneNumber().getValue());
    }
}
