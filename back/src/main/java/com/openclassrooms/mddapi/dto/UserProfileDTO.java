package com.openclassrooms.mddapi.dto;

import java.util.List;

import com.openclassrooms.mddapi.model.Themes;

import lombok.Data;

// DTO pour représenter le profil d'un utilisateur
@Data
public class UserProfileDTO {

    // Nom d'utilisateur de l'utilisateur.
    private String username;

    // Adresse e-mail de l'utilisateur.
    private String email;

    // Liste des thèmes auxquels l'utilisateur est abonné.
    private List<Themes> themes;
}
