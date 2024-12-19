package com.openclassrooms.mddapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto{
        private Long id;
        private String content;
        private Long postId;
        private String username;
}