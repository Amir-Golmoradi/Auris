package dev.auris.identity.infrastructure.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Identity", description = "Security diagnostic endpoints")
public class IntroController {
    @GetMapping("/member")
    @PreAuthorize("hasAuthority('MEMBER')")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Validate MEMBER authority")
    public String helloMember() {
        return "Welcome, Member";
    }

    @GetMapping("/owner")
    @PreAuthorize("hasAuthority('OWNER')")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Validate OWNER authority")
    public String helloOwner() {
        return "Welcome, Owner";
    }

}
