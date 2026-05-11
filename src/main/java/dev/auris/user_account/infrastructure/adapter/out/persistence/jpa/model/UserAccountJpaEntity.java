package dev.auris.user_account.infrastructure.adapter.out.persistence.jpa.model;

import dev.auris.user_account.domain.enums.UserStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "user_account")
public class UserAccountJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(nullable = false, updatable = false)
    private OffsetDateTime createdAt;
    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;
    @Column(nullable = false)
    private String phoneNumber;
    @Column(name = "description")
    private String description;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserStatus status;

    public UserAccountJpaEntity() {
    }

    public UserAccountJpaEntity(
            UUID id,
            OffsetDateTime createdAt,
            String username,
            String email,
            String password,
            String firstName,
            String lastName,
            String phoneNumber,
            String description,
            UserStatus status
    ) {
        this.id = id;
        this.createdAt = createdAt;
        this.username = username;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.description = description;
        this.status = status;
    }

    public static Builder builder() {
        return new Builder();
    }

    public UUID getId() {
        return id;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public String getLastName() {
        return lastName;
    }

    public UserStatus getStatus() {
        return status;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getDescription() {
        return description;
    }

    public static class Builder {

        private UUID id;
        private OffsetDateTime createdAt;
        private String username;
        private String email;
        private String password;
        private String firstName;
        private String lastName;
        private String phoneNumber;
        private String description;
        private UserStatus status;

        public Builder id(UUID id) {
            this.id = id;
            return this;
        }

        public Builder createdAt(OffsetDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Builder username(String username) {
            this.username = username;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder phoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder status(UserStatus status) {
            this.status = status;
            return this;
        }

        public UserAccountJpaEntity build() {
            Objects.requireNonNull(createdAt, "createdAt must not be null");
            Objects.requireNonNull(username, "username must not be null");
            Objects.requireNonNull(email, "email must not be null");
            Objects.requireNonNull(password, "password must not be null");
            Objects.requireNonNull(firstName, "firstName must not be null");
            Objects.requireNonNull(lastName, "lastName must not be null");
            Objects.requireNonNull(phoneNumber, "phoneNumber must not be null");
            Objects.requireNonNull(status, "status must not be null");

            return new UserAccountJpaEntity(
                    id,
                    createdAt,
                    username,
                    email,
                    password,
                    firstName,
                    lastName,
                    phoneNumber,
                    description,
                    status
            );
        }
    }
}
