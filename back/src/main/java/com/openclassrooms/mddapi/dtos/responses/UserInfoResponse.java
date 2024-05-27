package com.openclassrooms.mddapi.dtos.responses;

import com.openclassrooms.mddapi.model.Topic;
import lombok.*;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class UserInfoResponse {
    String email;
    String username;
    Set<Topic> topicsIds;
}
