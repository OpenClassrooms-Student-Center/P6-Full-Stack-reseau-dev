package com.openclassrooms.mddapi.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    @Email
    private String email;
    private String username;

    @JsonIgnore
    @Size(max = 120)
    private String password;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}