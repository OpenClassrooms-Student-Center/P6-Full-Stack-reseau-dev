package com.openclassrooms.mddapi.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

// Classe représentant un message dans le système
@Data
@Entity // Annotation pour indiquer que cette classe est une entité JPA
@Table(name = "Messages") // Spécifie le nom de la table dans la base de données
public class Messages {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Integer id; // Identifiant unique du message

    // Relation Many-to-One avec User (l'utilisateur qui a créé le message)
    @ManyToOne(fetch = FetchType.EAGER) // Charge l'utilisateur de manière immédiate
    @JoinColumn(name = "users_id") // Colonne référencée pour l'utilisateur
    private User user; // Référence à l'utilisateur

    // Relation Many-to-One avec Article (l'article auquel le message est associé)
    @ManyToOne(fetch = FetchType.EAGER) // Charge l'article de manière immédiate
    @JoinColumn(name = "Article_id") // Colonne référencée pour l'article
    private Article article; // Référence à l'article

    // Contenu du message
    @Size(max = 5000) 
    @NotNull 
    private String message;

    @Column(name = "created_at", updatable = false) // Colonne pour la date de création, non modifiable
    private LocalDateTime createdAt; 

    @Column(name = "updated_at") 
    private LocalDateTime updatedAt; 

    // Méthode appelée avant l'insertion d'un message dans la base de données
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    // Méthode appelée avant la mise à jour d'un message dans la base de données
    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
