package com.openclassrooms.mddapi.dto;

// DTO pour représenter une requête de connexion d'utilisateur
public class UserLoginRequest {

    // Adresse e-mail de l'utilisateur.
    private String email;

    // Mot de passe de l'utilisateur.
    private String password;

    // Getter pour l'e-mail.
    public String getEmail() {
        return email;
    }

    // Setter pour l'e-mail.
    public void setEmail(String email) {
        this.email = email;
    }

    // Getter pour le mot de passe.
    public String getPassword() {
        return password;
    }

    // Setter pour le mot de passe.
    public void setPassword(String password) {
        this.password = password;
    }
}
