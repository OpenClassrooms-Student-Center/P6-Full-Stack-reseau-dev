package com.openclassrooms.mddapi.dto;

import lombok.Data;

// DTO pour représenter une requête de création ou de mise à jour d'un article
@Data
public class ArticleRequestDto {

    // Titre de l'article, fourni lors de la création ou de la mise à jour.
    private String title;

    // Description de l'article, fournie lors de la création ou de la mise à jour.
    private String description;

    // Identifiant du thème auquel l'article est associé.
    private Long theme;
}
