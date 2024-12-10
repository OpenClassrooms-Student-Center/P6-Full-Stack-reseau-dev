package com.openclassrooms.mddapi.configuration;

import com.openclassrooms.mddapi.dto.CommentDto;
import com.openclassrooms.mddapi.dto.UserProfilDto;
import com.openclassrooms.mddapi.model.ArticleModel;
import com.openclassrooms.mddapi.model.CommentModel;
import com.openclassrooms.mddapi.model.UserModel;

import java.util.Date;

public class CommentMapper {
    public static CommentDto toDTO(CommentModel comment) {
        if (comment == null) {
            return null;
        }

        CommentDto dto = new CommentDto();
        dto.setId(comment.getId());
        dto.setContent(comment.getContent());
        dto.setPublishedDate(comment.getCreatedAt());

        UserProfilDto authorDto = new UserProfilDto(
                comment.getAuthor().getUsername(),
                comment.getAuthor().getEmail()
        );
        dto.setAuthor(authorDto);

        return dto;
    }

    public static CommentModel toModel(CommentDto dto, ArticleModel article, UserModel user) {
        if (dto == null) {
            return null;
        }

        CommentModel comment = new CommentModel();
        comment.setContent(dto.getContent());
        comment.setCreatedAt(new Date());
        comment.setArticle(article);
        comment.setAuthor(user);

        return comment;
    }
}
