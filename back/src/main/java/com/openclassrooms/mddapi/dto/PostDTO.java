package com.openclassrooms.mddapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class PostDTO {
    @NotNull
    @NotEmpty
    private Long id;
    @NotNull
    @NotEmpty
    private Long topicId;
    @NotNull
    @NotEmpty
    private Long userId;
    @NotNull
    @NotEmpty
    private String title;
    private String content;
    @JsonProperty("created_at")
    private String createdAt;
}
