package com.openclassrooms.mddapi.controller;

// Importation des classes nécessaires
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.mddapi.model.Article;
import com.openclassrooms.mddapi.model.Themes;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.service.ArticleService;
import com.openclassrooms.mddapi.service.ThemesService;
import com.openclassrooms.mddapi.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

// Annotation pour indiquer que cette classe est un contrôleur REST
@RestController
// Permet les requêtes Cross-Origin (CORS)
@CrossOrigin
// Définit le chemin de base pour les requêtes de l'API
@RequestMapping("/api")
// Documentation Swagger pour l'API
@Api(tags = "Articles", description = "Operations related to Articles")
public class ArticleController {
    
    // Injection de dépendance pour le service des articles
    @Autowired
    private ArticleService articleService;
    
    // Injection de dépendance pour le service des utilisateurs
    @Autowired
    private UserService userService;

    @Autowired
    private ThemesService themesService;

    // Méthode pour obtenir tous les articles
    @GetMapping("/articles")
    @ApiOperation(value = "Get all articles", notes = "Returns a list of all Articles.")
    public Map<String, List<Article>> getArticle() {
        // Appel du service pour récupérer la liste des articles
        List<Article> articleList = (List<Article>) articleService.getArticle();
        
        // Création d'une réponse sous forme de Map
        Map<String, List<Article>> response = new HashMap<>(); // Utilisation de l'inférence de type
        response.put("articles", articleList); // Ajout de la liste des articles dans la réponse
        return response; // Retourne la réponse
    }
    
    // Méthode pour obtenir un article par son ID
    @GetMapping("articles/{id}")
    @ApiOperation(value = "Get Article by ID", notes = "Returns an Article by its ID.")
    public ResponseEntity<Article> getRentalById(@PathVariable Long id) {
        // Récupération de l'article par son ID
        Optional<Article> article = articleService.getArticleById(id);
        
        // Vérifie si l'article est présent
        if (article.isPresent()) {
            return new ResponseEntity<>(article.get(), HttpStatus.OK); // Retourne l'article avec le statut 200 OK
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Retourne 404 Not Found si l'article n'existe pas
        }
    }

    // Méthode pour créer un nouvel article
    @PostMapping(value = "/articles")
    @ApiOperation(value = "Create a new Article", notes = "Creates a new Article.")
    public Article saveRentals(@RequestHeader("Authorization") String authorizationHeader,
                               @RequestParam("title") String title,
                               @RequestParam("description") String description,
                               @RequestParam("theme") String theme){
        // Extraction du token d'authentification du header
        String token = authorizationHeader.substring("Bearer ".length()).trim();
        
        // Récupération de l'utilisateur à partir du token
        User user = userService.getUserByToken(token);
        
        // Création d'une nouvelle instance d'article
        Long themeId = Long.parseLong(theme);
        System.err.println("theme: " + themeId);
        Themes themeIdObject = themesService.getThemesById(themeId);   
        Article article = new Article();
        article.setTitle(title); // Définition du titre de l'article
        article.setDescription(description); // Définition de la description de l'article
        article.setAuthor(user); // Attribution de l'auteur à l'article
        article.setTheme(themeIdObject);

        // Enregistrement de l'article via le service
        Article savedRentals = articleService.saveArticles(article);
                                
        if (savedRentals != null) {
            return new ResponseEntity<>(savedRentals, HttpStatus.OK).getBody();
        } else {
            return null;
        }
        }
            // find article by theme
    @GetMapping("/articles/theme/{themeId}")
    public List<Article> getArticlesByTheme(@PathVariable Long themeId) {
        Themes theme = themesService.getThemesById(themeId);
        return articleService.getArticlesByTheme(theme);
    }
}
