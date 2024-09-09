package com.openclassrooms.p6.payload.response;

import java.util.List;

public record MultipleArticlesResponse(
                List<ArticleSummaryResponse> articles) {
}