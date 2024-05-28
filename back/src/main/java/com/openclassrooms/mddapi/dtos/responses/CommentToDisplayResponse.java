package com.openclassrooms.mddapi.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentToDisplayResponse {
    private Long id;
    private String text;
    private String authorName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
