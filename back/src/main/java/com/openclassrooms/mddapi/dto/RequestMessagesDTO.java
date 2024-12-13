package com.openclassrooms.mddapi.dto;

import lombok.Data;

// DTO pour représenter une requête de message simple
@Data
public class RequestMessagesDTO {

    // Contenu du message à traiter.
    private String message;
}
