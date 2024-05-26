package com.openclassrooms.mddapi.dtos.responses;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class UserInfoResponse {
    String email;
    String username;
}
