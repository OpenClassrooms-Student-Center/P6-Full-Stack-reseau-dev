package com.openclassrooms.mddapi.configuration;

import com.openclassrooms.mddapi.dto.ArticleDto;
import com.openclassrooms.mddapi.dto.AuthorDto;
import com.openclassrooms.mddapi.dto.CommentDto;
import com.openclassrooms.mddapi.dto.ThemeDto;
import com.openclassrooms.mddapi.model.ArticleModel;

import java.util.List;
import java.util.stream.Collectors;

public class ArticleMapper {

    public static ArticleDto toDTO(ArticleModel article) {
        if (article == null) {
            return null;
        }

        ArticleDto dto = new ArticleDto();
        dto.setId(article.getId());
        dto.setTitle(article.getTitle());
        dto.setContent(article.getContent());
        dto.setPublishedDate(article.getPublishedDate());

        ThemeDto themeDTO = ThemeMapper.toDTO(article.getTheme());
        dto.setTheme(themeDTO);

        AuthorDto authorDTO = com.openclassrooms.mddapi.configuration.AuthorMapper.toDTO(article.getAuthor());
        dto.setAuthor(authorDTO);

        List<CommentDto> commentDtos = article.getComments().stream()
                .map(CommentMapper::toDTO)
                .collect(Collectors.toList());
        dto.setComments(commentDtos);

        return dto;
    }
}
