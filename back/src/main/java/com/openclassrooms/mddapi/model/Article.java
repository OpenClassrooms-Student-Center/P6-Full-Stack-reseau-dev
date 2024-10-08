package com.openclassrooms.mddapi.model;

import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

// Annotation pour indiquer que cette classe est une entité JPA
@Entity
@Data // Génère automatiquement les getters, setters, et autres méthodes
@Table(name = "Article") // Spécifie le nom de la table dans la base de données
public class Article {

    @Id // Indique que ce champ est la clé primaire
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Génération automatique de la valeur ID
    private Long id;

    @NotNull
    private String title; // Titre de l'article

    @Size(max = 5000) // Limite la taille de la description à 5000 caractères
    @NotNull
    private String description; // Description de l'article

    // Relation Many-to-Many avec Messages
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "article_Messages", // Nom de la table de jointure
            joinColumns = @JoinColumn(name = "Article_id"), // Colonne référencée pour l'article
            inverseJoinColumns = @JoinColumn(name = "Messages_id") // Colonne référencée pour les messages
    )
    private Set<Messages> messages; // Ensemble des messages associés à l'article

    // Relation Many-to-One avec User (l'auteur de l'article)
    @ManyToOne(fetch = FetchType.LAZY) // Charge l'auteur de manière paresseuse
    @JoinColumn(name = "author_id", referencedColumnName = "id") // Colonne référencée pour l'auteur
    private User author; // Auteur de l'article

    // Relation Many-to-One avec Themes (le thème de l'article)
    @ManyToOne(fetch = FetchType.LAZY) // Charge le thème de manière paresseuse
    @JoinColumn(name = "theme_id", referencedColumnName = "id") // Colonne référencée pour le thème
    private Themes theme; // Thème de l'article

    @Column(name = "created_at", updatable = false) // Colonne pour la date de création, non modifiable
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Méthode appelée avant l'insertion de l'article dans la base de données
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    // Méthode appelée avant la mise à jour de l'article dans la base de données
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
