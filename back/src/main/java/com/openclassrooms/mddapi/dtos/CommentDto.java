package com.openclassrooms.mddapi.dtos;

import lombok.*;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {
    private Long id;
    private String text;
    private Long postId;
    private Long authorId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}