package dev.auris.identity.infrastructure.web;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IntroController {
    @GetMapping("/member")
    @PreAuthorize("hasAuthority('MEMBER')")
    @ResponseStatus(HttpStatus.OK)
    public String helloMember() {
        return "Welcome, Member";
    }

    @GetMapping("/owner")
    @PreAuthorize("hasAuthority('OWNER')")
    @ResponseStatus(HttpStatus.OK)
    public String helloOwner() {
        return "Welcome, Owner";
    }

}
