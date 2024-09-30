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
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Entity  // Indique que cette classe est une entité JPA
@Data  // Génère automatiquement les méthodes getter, setter, toString, hashCode et equals via Lombok
@Table(name = "Article")  // Spécifie le nom de la table correspondante dans la base de données
public class Article {

    @Id  // Indique que ce champ est la clé primaire
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Indique que la valeur sera générée automatiquement par la base de données
    private Long id;

    @NotNull  // Indique que ce champ ne peut pas être nul
    private String title;  // Titre de l'article

    @Size(max = 5000)  // Limite la taille maximale du champ description à 5000 caractères
    @NotNull  // Indique que ce champ ne peut pas être nul
    private String description;  // Description de l'article

    @ManyToOne(fetch = FetchType.EAGER)  // Relation Many-to-One avec l'entité User, chargement des données de l'auteur de manière anticipée
    @JoinColumn(name = "author_id", referencedColumnName = "id")  // Indique la colonne qui contient la clé étrangère
    private User author;  // Référence à l'auteur de l'article

    @Column(name = "created_at")  // Spécifie le nom de la colonne dans la base de données pour la date de création
    private LocalDateTime createdAt;  // Date et heure de création de l'article

    @Column(name = "updated_at")  // Spécifie le nom de la colonne dans la base de données pour la date de mise à jour
    private LocalDateTime updatedAt;  // Date et heure de mise à jour de l'article
}
