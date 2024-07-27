package com.openclassrooms.microserviceuser.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegisterDto {
    public String username;
    public String email;
    public String password;
}