package com.openclassrooms.mddapi.dto;
import java.util.List;

import com.openclassrooms.mddapi.model.Themes;

import lombok.Data;
@Data
public class ArticleWithMessagesDTO {
    private Long id;
    private String title;
    private String description;
    private String username;
    private Themes Themes;
    private String created_at;
    private List<MessageDTO> messages;
}