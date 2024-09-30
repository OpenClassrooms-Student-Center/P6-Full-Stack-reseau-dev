package com.openclassrooms.mddapi.controller;

// Importation des classes nécessaires
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.mddapi.model.Themes;
import com.openclassrooms.mddapi.service.ThemesService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

// Annotation pour indiquer que cette classe est un contrôleur REST
@RestController
// Permet les requêtes Cross-Origin (CORS)
@CrossOrigin
// Définit le chemin de base pour les requêtes de l'API
@RequestMapping("/api")
// Documentation Swagger pour l'API
@Api(tags = "Themes", description = "Operations related to Themes")
public class ThemesController {
    
    // Injection de dépendance pour le service des thèmes
    @Autowired
    private ThemesService themesService;

    // Méthode pour obtenir tous les thèmes
    @GetMapping("/themes")
    @ApiOperation(value = "Get all themes", notes = "Returns a list of all themes.")
    public HashMap<String, List<Themes>> getThemes() {
        // Appel du service pour récupérer la liste des thèmes
        List<Themes> themesList = (List<Themes>) themesService.getThemes();
        
        // Création d'une réponse sous forme de HashMap
        HashMap<String, List<Themes>> response = new HashMap<>(); // Utilisation de l'inférence de type
        response.put("themes", themesList); // Ajout de la liste des thèmes dans la réponse
        return response; // Retourne la réponse
    }
    
    // Méthode pour obtenir un thème par son ID
    @GetMapping("/theme/{id}")
    @ApiOperation(value = "Get theme by ID", notes = "Returns a theme by its ID.")
    public Themes getThemeById(Long id) {
        // Appel du service pour récupérer un thème par son ID
        return themesService.getThemesById(id); // Retourne le thème trouvé
    }

    // Méthode pour enregistrer un nouveau thème
    @PostMapping("/themes")
    @ApiOperation(value = "Create a new theme", notes = "Creates a new theme.")
    public Themes saveThemes(Themes themes) {
        // Appel du service pour sauvegarder le thème
        Themes savedThemes = themesService.saveThemes(themes); 
        return savedThemes; // Retourne le thème sauvegardé
    }
}
