package com.openclassrooms.mddapi.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ArticleDto {
    private Long id;
    private String title;
    private String content;
    private AuthorDto author;
    private ThemeDto theme;
    private Date publishedDate;
    private List<CommentDto> comments;
}