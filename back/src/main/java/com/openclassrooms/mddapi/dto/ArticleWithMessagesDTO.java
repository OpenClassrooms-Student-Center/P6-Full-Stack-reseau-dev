package com.openclassrooms.mddapi.dto;

import java.util.List;

import lombok.Data;

@Data
public class ArticleWithMessagesDTO {
    private Long id;
    private String title;
    private String description;
    private String username;
    private String themeTitle;  // Champ pour stocker le titre du thème
    private List<MessageDTO> messages;
    private String createdAt;  // Date de création
    private String updatedAt;  // Date de mise à jour
}
