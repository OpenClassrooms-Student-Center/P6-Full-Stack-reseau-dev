package com.openclassrooms.mddapi.dto;

import lombok.Data;

// DTO pour représenter un message associé à un article
@Data
public class MessageDTO {

    // Identifiant unique du message.
    private Integer id;

    // Nom d'utilisateur de la personne qui a écrit le message.
    private String userUsername;

    // Contenu du message.
    private String message;
}
