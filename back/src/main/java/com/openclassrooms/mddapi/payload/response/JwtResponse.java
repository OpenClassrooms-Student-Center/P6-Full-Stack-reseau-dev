package com.openclassrooms.mddapi.payload.response;

import lombok.*;

@Data
@Getter
@Setter
@ToString
@Builder
public class JwtResponse {

  private Long id;
  private String email;
  private String username;
  public String type ;
  private String token;


  public JwtResponse(Long id, String email, String username,String type, String token) {
    this.email = email;
    this.id = id;
    this.token = token;
    this.username = username;
    this.type ="Bearer";
  }
}
