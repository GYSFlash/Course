package com.hotel.dto;

import jakarta.validation.constraints.NotNull;

public class UserRegisterDTO {
    @NotNull
    private String username;
    @NotNull
    private String password;
    @NotNull
    private ClientRequestDTO client;
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

    public @NotNull ClientRequestDTO getClient() {
        return client;
    }

    public void setClient(@NotNull ClientRequestDTO client) {
        this.client = client;
    }
}
