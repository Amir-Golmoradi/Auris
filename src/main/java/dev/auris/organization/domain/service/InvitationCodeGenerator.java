package dev.auris.organization.domain.service;

public interface InvitationCodeGenerator {
    String generate(int length);
}
