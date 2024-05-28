package com.openclassrooms.mddapi.dtos.requests;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class NewCommentRequest {
    private String comment;
    private Long postId;
}
