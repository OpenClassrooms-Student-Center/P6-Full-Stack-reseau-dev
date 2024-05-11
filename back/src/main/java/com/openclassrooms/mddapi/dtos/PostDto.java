package com.openclassrooms.mddapi.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {
    private Long id;

    @NotNull
    private Long topicId;

    @NotNull
    private String article;

    private Long authorId;

    private List<Long> commentIds;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}