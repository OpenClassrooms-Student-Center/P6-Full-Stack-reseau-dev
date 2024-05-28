package com.openclassrooms.mddapi.dtos.requests;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class NewPostRequest {
    private Long topicId;
    private String title;
    private String content;
}
