package com.openclassrooms.mddapi.dtos.requests;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class NewUserInfoRequest {

    private String username;
    private String email;
}
