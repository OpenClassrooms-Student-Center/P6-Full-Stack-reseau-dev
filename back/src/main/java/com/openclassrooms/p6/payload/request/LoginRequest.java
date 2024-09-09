package com.openclassrooms.p6.payload.request;

import jakarta.validation.constraints.NotBlank;

/**
 * Request payload for user login.
 */
public record LoginRequest(
                @NotBlank(message = "Email or username cannot be blank or null") String identifier,
                @NotBlank(message = "Password cannot be blank or null") String password) {
}