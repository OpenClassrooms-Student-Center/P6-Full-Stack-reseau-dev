package com.openclassrooms.p6.payload.response;

import java.time.LocalDateTime;

public record ArticleSummaryResponse(
                Long id,
                String username,
                Long articleId,
                String title,
                String description,
                LocalDateTime publicationDate) {
}