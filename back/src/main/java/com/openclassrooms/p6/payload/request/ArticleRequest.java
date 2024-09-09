package com.openclassrooms.p6.payload.request;

import jakarta.validation.constraints.NotBlank;

public record ArticleRequest(
        @NotBlank(message = "Title cannot be blank or null") String title,
        @NotBlank(message = "Description cannot be blank or null") String description) {
}
