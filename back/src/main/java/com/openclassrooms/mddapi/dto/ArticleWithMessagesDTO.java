package com.openclassrooms.mddapi.dto;

import java.util.List;

import lombok.Data;

@Data
public class ArticleWithMessagesDTO {
// Identifiant unique de l'article.

    private Long id;

// Titre de l'article, fournissant un résumé ou une indication du contenu.
    private String title;

// Description de l'article, donnant plus de contexte au contenu.
    private String description;

// Nom d'utilisateur de l'auteur de l'article ou de la personne qui a posté le message.
    private String username;

// Champ pour stocker le titre du thème associé à l'article.
    private String themeTitle;

// Liste des messages associés à l'article, contenant des objets de type MessageDTO.
    private List<MessageDTO> messages;

// Date de création de l'article, généralement au format ISO ou une chaîne formatée.
    private String createdAt;

// Date de mise à jour de l'article, indiquant quand il a été modifié pour la dernière fois.
    private String updatedAt;
}
