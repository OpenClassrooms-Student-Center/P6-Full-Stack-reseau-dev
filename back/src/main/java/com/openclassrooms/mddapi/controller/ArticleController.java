package com.openclassrooms.mddapi.controller;

// Importation des classes nécessaires
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.mddapi.dto.ArticleRequestDto;
import com.openclassrooms.mddapi.model.Article;
import com.openclassrooms.mddapi.model.Messages;
import com.openclassrooms.mddapi.model.Themes;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.service.ArticleService;
import com.openclassrooms.mddapi.service.MessagesService;
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
    private MessagesService messagesService;

    @Autowired
    private ThemesService themesService;

    // Méthode pour obtenir tous les articles
    @GetMapping("/articles")
    @ApiOperation(value = "Get all articles", notes = "Returns a list of all Articles.")
    public Map<String, List<Article>> getArticle() {
        // Appel du service pour récupérer la liste des articles
        List<Article> articleList = (List<Article>) articleService.getArticle();
        
        // Création d'une réponse sous forme de Map
        Map<String, List<Article>> response = new HashMap<>();
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
        // Méthode pour obtenir tous les messages associés à un article via son articleId
        @GetMapping("/articles/{articleId}/messages")
        public ResponseEntity<Set<Messages>> getMessagesByArticleId(@PathVariable Long articleId) {
            Set<Messages> messages = messagesService.getMessagesByArticleId(articleId);
            if (messages.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);  // 404 si aucun message n'est trouvé
            } else {
                return new ResponseEntity<>(messages, HttpStatus.OK);  // Retourne l'ensemble des messages avec statut 200 OK
            }
        }
        // post message by article_id
        @PostMapping("/articles/{articleId}/messages")
        public ResponseEntity<Messages> saveMessages(@RequestHeader("Authorization") String authorizationHeader,
                                                    @PathVariable Long articleId, 
                                                    @RequestParam String message) {
                        
            String token = authorizationHeader.substring("Bearer ".length()).trim();
            User user = userService.getUserByToken(token);
            Article article = articleService.getArticleById(articleId).get();
            Messages newMessage = new Messages();
            newMessage.setUser(user);
            newMessage.setArticle(article);
            newMessage.setMessage(message);
            Messages savedMessage = messagesService.saveMessage(newMessage);
            article.getMessages().add(savedMessage);
            if (savedMessage != null) {
                return new ResponseEntity<>(savedMessage, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
                                                        
        }

    // Méthode pour créer un nouvel article
    @PostMapping(value = "/articles")
    @ApiOperation(value = "Create a new Article", notes = "Creates a new Article.")
    public Article saveRentals(@RequestHeader("Authorization") String authorizationHeader,
                                 @RequestBody ArticleRequestDto articleDto) {
        // Extraction du token d'authentification du header
        String token = authorizationHeader.substring("Bearer ".length()).trim();
        
        // Récupération de l'utilisateur à partir du token
        User user = userService.getUserByToken(token);
        
        // Création d'une nouvelle instance d'article
        Long themeId = articleDto.getTheme();
        Themes themeIdObject = themesService.getThemesById(themeId);   
        Article article = new Article() ;
        article.setTitle(articleDto.getTitle());
        article.setDescription(articleDto.getDescription());
        article.setAuthor(user); // Attribution de l'auteur à l'article
        article.setTheme(themeIdObject);
        System.err.println("article: " + article);


        // Enregistrement de l'article via le service
        Article savedRentals = articleService.saveArticles(article);
                                
        if (savedRentals != null) {
            return new ResponseEntity<>(savedRentals, HttpStatus.OK).getBody();
        } else {
            return null;
        }
    }
    
    // Méthode pour récupérer des articles par thème
    @GetMapping("/articles/theme/{themeId}")
    public List<Article> getArticlesByTheme(@PathVariable Long themeId) {
        Themes theme = themesService.getThemesById(themeId);
        return articleService.getArticlesByTheme(theme);
    }
}
