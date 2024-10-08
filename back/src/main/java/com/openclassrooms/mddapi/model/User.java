package com.openclassrooms.mddapi.model;

import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;

// Classe représentant un utilisateur dans le système
@Entity // Annotation pour indiquer que cette classe est une entité JPA
@Data // Génère automatiquement les getters, setters, et autres méthodes utiles
@Table(name = "User") // Spécifie le nom de la table dans la base de données
public class User {

    @Id // Indique que ce champ est la clé primaire
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Génération automatique de la valeur ID
    private Long id; // Identifiant unique de l'utilisateur

    @NotNull 
    private String email; // Adresse e-mail de l'utilisateur

    @NotNull 
    private String username; // Nom d'utilisateur

    @NotNull 
    private String password; // Mot de passe de l'utilisateur

    // Relation Many-to-Many avec Themes, permettant de lier plusieurs thèmes à un utilisateur
    @ManyToMany(fetch = FetchType.EAGER) // Charge les thèmes de manière immédiate
    @JoinTable(name = "user_Themes", // Nom de la table de jointure
            joinColumns = @JoinColumn(name = "user_id"), // Colonne référencée pour l'utilisateur
            inverseJoinColumns = @JoinColumn(name = "Themes_id")) // Colonne référencée pour les thèmes
    private Set<Themes> themes; // Ensemble de thèmes associés à l'utilisateur

    @Column(name = "created_at") 
    private LocalDateTime createdAt;

    @Column(name = "updated_at") 
    private LocalDateTime updatedAt; 

    // Getter pour la collection de thèmes
    public Set<Themes> getThemes() {
        return themes;
    }

    // Setter pour la collection de thèmes
    public void setThemes(Set<Themes> themes) {
        this.themes = themes;
    }
}
