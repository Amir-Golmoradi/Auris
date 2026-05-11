package dev.auris.organization.domain.service;

import dev.auris.organization.domain.value_object.InvitationCode;

public interface InvitationCodeGenerator {
    InvitationCode generate(int length);
}
