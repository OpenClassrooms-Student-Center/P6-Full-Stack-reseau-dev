package com.openclassrooms.mddapi.dto;

import lombok.Data;

// DTO pour représenter une requête de création de message
@Data
public class PostMessagesDto {

    // Contenu du message à poster.
    private String message;

    // Identifiant de l'article auquel le message est associé.
    private Long article_id;

    // Identifiant de l'utilisateur qui envoie le message.
    private Long user_id;
}
