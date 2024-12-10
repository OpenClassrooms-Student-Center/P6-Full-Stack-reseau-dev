package com.openclassrooms.mddapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Schema(description = "DTO for authentication token")

public class TokenDto {
    @Schema(description = "JWT authentication token", example = "eyJhbGciOiJIUzI1NiIsInR...")
    private String token;
    private long id;
    private String username;
    private String email;
}
