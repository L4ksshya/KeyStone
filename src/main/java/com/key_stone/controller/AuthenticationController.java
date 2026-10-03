package com.key_stone.controller;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.key_stone.dto.LoginRequestDTO;
import com.key_stone.dto.LoginResponseDTO;
import com.key_stone.service.UserAuthenticationService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.security.access.prepost.PreAuthorize;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    private final UserAuthenticationService userAuthenticationService;

    public AuthenticationController(
            UserAuthenticationService userAuthenticationService) {
        this.userAuthenticationService = userAuthenticationService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(
            @Valid @RequestBody LoginRequestDTO request) {

        LoginResponseDTO response =
                userAuthenticationService.login(request);

        return ResponseEntity.ok(response);
    }
    @GetMapping("/admin-test")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String adminTest() {
        return "Welcome Admin";
    }
}