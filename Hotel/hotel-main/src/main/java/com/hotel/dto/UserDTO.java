package com.hotel.dto;

import jakarta.validation.constraints.NotNull;

public class UserDTO {
    @NotNull
    private String username;
    @NotNull
    private String password;

    public @NotNull String getUsername() {
        return username;
    }

    public void setUsername(@NotNull String username) {
        this.username = username;
    }

    public @NotNull String getPassword() {
        return password;
    }

    public void setPassword(@NotNull String password) {
        this.password = password;
    }
}
