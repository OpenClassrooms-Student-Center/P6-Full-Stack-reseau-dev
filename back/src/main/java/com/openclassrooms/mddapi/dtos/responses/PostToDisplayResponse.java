package com.openclassrooms.mddapi.dtos.responses;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostToDisplayResponse {
    private Long id;

    @NotNull
    private String topicName;

    @NotNull
    private String article;

    @NotNull
    private String title;

    private String authorName;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}

