package com.openclassrooms.mddapi.dto;

import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ArticleCreateDto {
    private String title;
    private String content;
    private Long authorId;
    private Long themeId;
}
