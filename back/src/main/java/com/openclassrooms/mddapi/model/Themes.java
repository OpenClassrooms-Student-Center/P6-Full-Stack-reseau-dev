package com.openclassrooms.mddapi.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;
// Classe représentant un thème dans le système

@Data
@Entity // Annotation pour indiquer que cette classe est une entité JPA
@Table(name = "Themes") // Spécifie le nom de la table dans la base de données
public class Themes {

    @Id // Indique que ce champ est la clé primaire
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Génération automatique de la valeur ID
    private Long id; // Identifiant unique du thème

    @NotNull // Indique que le titre ne peut pas être nul
    private String title; // Titre du thème

    @NotNull // Indique que la description ne peut pas être nulle
    private String description; // Description du thème

    @Column(name = "created_at") 
    private LocalDateTime createdAt; 

    @Column(name = "updated_at") 
    private LocalDateTime updatedAt; 

}
