package com.key_stone.dto;

import jakarta.validation.constraints.NotBlank;

public class UpdateUserRequestDTO {

    @NotBlank(message = "Username is required")
    private String username;

    public UpdateUserRequestDTO() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}