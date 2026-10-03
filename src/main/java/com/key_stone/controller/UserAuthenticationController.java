package com.key_stone.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.key_stone.dto.UserRequestDTO;
import com.key_stone.dto.UserResponseDTO;
import com.key_stone.service.UserAuthenticationService;

import org.springframework.web.bind.annotation.PatchMapping;

import com.key_stone.dto.ChangePasswordRequestDTO;

import org.springframework.security.core.Authentication;

import com.key_stone.dto.UpdateUserRequestDTO;

@RestController
@RequestMapping("/api/users")
public class UserAuthenticationController {

    private final UserAuthenticationService userAuthenticationService;

    public UserAuthenticationController(
            UserAuthenticationService userAuthenticationService) {
        this.userAuthenticationService = userAuthenticationService;
    }

    // CREATE
    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(
            @Valid @RequestBody UserRequestDTO request) {

        UserResponseDTO createdUser =
                userAuthenticationService.createUser(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdUser);
    }

    // GET ALL
    @GetMapping
    public List<UserResponseDTO> getAllUsers() {

        return userAuthenticationService.getAllUsers();
    }

    // GET BY ID
    @GetMapping("/{id}")
    public UserResponseDTO getUserById(
            @PathVariable Long id) {
 
        return userAuthenticationService.getUserById(id);
    }

    // UPDATE
    @PutMapping("/{id}")
    public UserResponseDTO updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UpdateUserRequestDTO request) {

        return userAuthenticationService.updateUser(id, request);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(
            @PathVariable Long id) {

        userAuthenticationService.deleteUser(id);

        return ResponseEntity.noContent().build();
    }
    @PatchMapping("/{id}/password")
    public ResponseEntity<Void> changePassword(
            @PathVariable Long id,
            @Valid @RequestBody ChangePasswordRequestDTO request,
            Authentication authentication) {

        String username = authentication.getName();

        userAuthenticationService.changePassword(
                id,
                username,
                request
        );

        return ResponseEntity.noContent().build();
    }
}