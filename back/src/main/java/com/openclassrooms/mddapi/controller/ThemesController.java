package com.openclassrooms.mddapi.controller;

// Importation des classes nécessaires
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired; // Pour l'injection de dépendances
import org.springframework.web.bind.annotation.CrossOrigin; // Pour permettre les requêtes CORS
import org.springframework.web.bind.annotation.GetMapping; // Pour gérer les requêtes GET
import org.springframework.web.bind.annotation.PostMapping; // Pour gérer les requêtes POST
import org.springframework.web.bind.annotation.RequestMapping; // Pour définir le chemin de la requête
import org.springframework.web.bind.annotation.RestController; // Pour indiquer que la classe est un contrôleur REST

import com.openclassrooms.mddapi.model.Themes; // Import du modèle Themes
import com.openclassrooms.mddapi.service.ThemesService; // Import du service ThemesService

import io.swagger.annotations.Api; // Pour la documentation Swagger
import io.swagger.annotations.ApiOperation; // Pour documenter les opérations de l'API

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
    private ThemesService themesService; // Service utilisé pour interagir avec les données des thèmes

    // Méthode pour obtenir tous les thèmes
    @GetMapping("/themes")
    @ApiOperation(value = "Get all themes", notes = "Returns a list of all themes.")
    public HashMap<String, List<Themes>> getThemes() {
        // Appel du service pour récupérer la liste des thèmes
        List<Themes> themesList = (List<Themes>) themesService.getThemes();

        // Création d'une réponse sous forme de HashMap
        HashMap<String, List<Themes>> response = new HashMap<>(); // Utilisation de l'inférence de type
        response.put("themes", themesList); // Ajout de la liste des thèmes dans la réponse
        return response; // Retourne la réponse contenant tous les thèmes
    }

    // Méthode pour obtenir un thème par son ID
    @GetMapping("/theme/{id}")
    @ApiOperation(value = "Get theme by ID", notes = "Returns a theme by its ID.")
    public Themes getThemeById(Long id) {
        // Appel du service pour récupérer un thème par son ID
        return themesService.getThemesById(id); // Retourne le thème trouvé ou null si non trouvé
    }

    // Méthode pour enregistrer un nouveau thème
    @PostMapping("/themes")
    @ApiOperation(value = "Create a new theme", notes = "Creates a new theme.")
    public Themes saveThemes(Themes themes) {
        // Appel du service pour sauvegarder le thème
        Themes savedThemes = themesService.saveThemes(themes); // Enregistre le thème dans la base de données
        return savedThemes; // Retourne le thème sauvegardé
    }
}
