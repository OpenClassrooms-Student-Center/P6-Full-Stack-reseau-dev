package com.openclassrooms.mddapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegisterDto {
    public String username;
    public String email;
    @NotBlank(message = "Password is required")
    @Pattern(
            regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[^a-zA-Z0-9]).{8,}$",
            message = "Password must be at least 8 characters long and contain at least one digit, one uppercase letter, one lowercase letter, and one special character"
    )
    public String password;
}
