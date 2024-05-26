package com.openclassrooms.mddapi.dtos.requests;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class RegisterRequest {

    String mail;
    String password;
    String username;

}
