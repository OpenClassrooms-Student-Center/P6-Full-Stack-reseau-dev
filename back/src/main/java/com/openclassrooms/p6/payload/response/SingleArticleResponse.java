package com.openclassrooms.p6.payload.response;

import java.time.LocalDateTime;
import java.util.List;

public record SingleArticleResponse(
                Long id,
                String authorName,
                LocalDateTime creationDate,
                String theme,
                String title,
                String description,
                List<CommentResponse> comments) {

}