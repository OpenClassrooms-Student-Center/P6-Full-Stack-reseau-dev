package com.openclassrooms.mddapi.dto;

import java.util.List;

import lombok.Data;

// DTO pour représenter un article avec ses messages associés
@Data
public class ArticleMessagesDTO {

    // Identifiant unique de l'article. Cela permet d'associer les messages à un article spécifique.
    private Long articleId;

    // Nom d'utilisateur de l'auteur de l'article.
    private String username;

    // Titre de l'article.
    private String title;

    // Description de l'article, fournissant plus de détails sur son contenu.
    private String description;

    // Liste des messages associés à l'article, contenant des objets de type MessageDTO.
    private List<MessageDTO> messages;

    // Date de création de l'article, généralement au format ISO ou une chaîne formatée.
    private String createdAt;

    // Date de mise à jour de l'article, indiquant quand il a été modifié pour la dernière fois.
    private String updatedAt;
}
