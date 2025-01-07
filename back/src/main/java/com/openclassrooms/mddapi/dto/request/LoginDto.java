package com.openclassrooms.mddapi.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginDto {
  @NotBlank
  private String identifier;

  @NotBlank
  private String password;
}