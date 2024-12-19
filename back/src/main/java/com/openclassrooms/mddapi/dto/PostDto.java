package com.openclassrooms.mddapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor @NoArgsConstructor
public class PostDto {
    private Long id;
    private String title;
    private String content;
    private String topicSubject;
    private LocalDateTime createdAt;
    private String authorUsername;

}
