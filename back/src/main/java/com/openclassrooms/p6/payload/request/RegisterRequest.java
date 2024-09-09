package com.openclassrooms.p6.payload.request;

import jakarta.validation.constraints.*;

/**
 * Request payload for user registration.
 */
public record RegisterRequest(
                @NotBlank(message = "Email cannot be blank or null") String email,
                @NotBlank(message = "Name cannot be blank or null") String username,
                @NotBlank(message = "Password cannot be blank or null") @Size(min = 8, message = "Password must be at least 8 characters long") @Pattern(regexp = "^(?=.*[0-9])(?=.*[!@#$%^&*()-+=<>?]).*$", message = "The password must contain at least one number and one special character") String password) {
}