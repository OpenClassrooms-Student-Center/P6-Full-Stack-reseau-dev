package com.openclassrooms.mddapi.payload.request;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SignupRequest {

  @Email
  private String email;


  private String username;


  @NotBlank
  //TODO implement password complexity check
  private String password;
}
