package com.openclassrooms.mddapi.service;

// Importation des classes nécessaires
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.mddapi.model.Themes;
import com.openclassrooms.mddapi.repository.ThemesRepository;

// Annotation pour indiquer que cette classe est un service Spring
@Service
public class ThemesService {
    
    // Injection de dépendance pour le dépôt de thèmes
    @Autowired
    private ThemesRepository themesRepository;
    
    // Méthode pour récupérer tous les thèmes
    public Iterable<Themes> getThemes() {
        // Appel du dépôt pour trouver tous les thèmes
        Iterable<Themes> themes = themesRepository.findAll();
        return themes; // Retourne la liste des thèmes
    }
    
    // Méthode pour récupérer un thème par son ID
    public Themes getThemesById(Long id) {
        // Appel du dépôt pour trouver un thème par ID
        return themesRepository.findById(id).orElse(null); // Retourne le thème ou null si non trouvé
    }
    
    // Méthode pour sauvegarder un nouveau thème
    public Themes saveThemes(Themes themes) {
        // Appel du dépôt pour sauvegarder le thème
        Themes savedThemes = themesRepository.save(themes);
        return savedThemes; // Retourne le thème sauvegardé
    }
    
    // Méthode pour supprimer un thème par ID
    public void deleteThemes(Long id) {
        // Appel du dépôt pour supprimer le thème par ID
        themesRepository.deleteById(id);
    }
    
    // Méthode pour mettre à jour un thème existant
    public Themes updateThemes(Themes themes) {
        // Appel du dépôt pour sauvegarder les modifications apportées au thème
        Themes updatedThemes = themesRepository.save(themes);
        return updatedThemes; // Retourne le thème mis à jour
    }
}
