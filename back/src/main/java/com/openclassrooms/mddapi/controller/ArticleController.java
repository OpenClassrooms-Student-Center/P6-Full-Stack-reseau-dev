package com.openclassrooms.mddapi.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.mddapi.dto.ArticleMessagesDTO;
import com.openclassrooms.mddapi.dto.ArticleRequestDto;
import com.openclassrooms.mddapi.dto.ArticleWithMessagesDTO;
import com.openclassrooms.mddapi.dto.MessageDTO;
import com.openclassrooms.mddapi.dto.PostMessagesDto;
import com.openclassrooms.mddapi.dto.RequestMessagesDTO;
import com.openclassrooms.mddapi.model.Article;
import com.openclassrooms.mddapi.model.Messages;
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

    // Injection de dépendance pour le service des thèmes
    @Autowired
    private ThemesService themesService;

    // Méthode pour obtenir tous les articles
    @GetMapping("/articles")
    @ApiOperation(value = "Get All Articles", notes = "Returns all Articles.")
    public ResponseEntity<Iterable<ArticleMessagesDTO>> getArticles() {
        // Appel du service pour récupérer tous les articles
        Iterable<ArticleMessagesDTO> articles = articleService.getArticles();
        if (articles != null) {
            return new ResponseEntity<>(articles, HttpStatus.OK); // Retourne les articles avec un statut 200 OK
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Retourne 404 Not Found si aucun article n'est trouvé
        }
    }

    // Méthode pour obtenir un article par son ID
    @GetMapping("articles/{id}")
    @ApiOperation(value = "Get Article by ID", notes = "Returns an Article by its ID.")
    public ResponseEntity<ArticleWithMessagesDTO> getArticleById(@PathVariable Long id) {
        // Récupération de l'article par son ID
        Optional<Article> articleOptional = articleService.getArticleById(id);
        if (articleOptional.isPresent()) {
            Article article = articleOptional.get();
            ArticleWithMessagesDTO articleWithMessagesDTO = new ArticleWithMessagesDTO();
            // Initialisation des données de l'article
            articleWithMessagesDTO.setId(article.getId());
            articleWithMessagesDTO.setTitle(article.getTitle());
            articleWithMessagesDTO.setDescription(article.getDescription());
            articleWithMessagesDTO.setUsername(article.getAuthor().getUsername());

            // Assigner le nom du thème ou une valeur par défaut
            if (article.getTheme() != null) {
                articleWithMessagesDTO.setThemeTitle(article.getTheme().getTitle());
            } else {
                articleWithMessagesDTO.setThemeTitle("Thème non défini");
            }

            // Date de création et mise à jour de l'article
            articleWithMessagesDTO.setCreatedAt(article.getCreatedAt().toString());
            articleWithMessagesDTO.setUpdatedAt(article.getUpdatedAt().toString());

            // Récupération des messages liés à l'article
            List<MessageDTO> messageDTOs = article.getMessages().stream()
                    .map(message -> {
                        MessageDTO messageDTO = new MessageDTO();
                        messageDTO.setId(message.getId());
                        messageDTO.setUserUsername(message.getUser().getUsername());
                        messageDTO.setMessage(message.getMessage());
                        return messageDTO;
                    })
                    .collect(Collectors.toList());
            articleWithMessagesDTO.setMessages(messageDTOs);

            return new ResponseEntity<>(articleWithMessagesDTO, HttpStatus.OK); // Retourne l'article avec les messages
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Retourne 404 Not Found si l'article n'existe pas
        }
    }

    // Méthode pour récupérer les messages d'un article spécifique
    @GetMapping("/articles/{articleId}/messages")
    public ResponseEntity<ArticleWithMessagesDTO> getMessageByArticleId(@PathVariable Long articleId) {
        Optional<Article> articleOptional = articleService.getArticleById(articleId);
        if (articleOptional.isPresent()) {
            Article article = articleOptional.get();
            ArticleWithMessagesDTO articleWithMessagesDTO = new ArticleWithMessagesDTO();
            // Initialisation des données de l'article
            articleWithMessagesDTO.setId(article.getId());
            articleWithMessagesDTO.setTitle(article.getTitle());
            articleWithMessagesDTO.setDescription(article.getDescription());

            // Récupérer le titre du thème ou une valeur par défaut
            if (article.getTheme() != null) {
                articleWithMessagesDTO.setThemeTitle(article.getTheme().getTitle());
            } else {
                articleWithMessagesDTO.setThemeTitle("Thème non défini");
            }

            articleWithMessagesDTO.setUsername(article.getAuthor().getUsername());
            articleWithMessagesDTO.setCreatedAt(article.getCreatedAt().toString());
            articleWithMessagesDTO.setUpdatedAt(article.getUpdatedAt().toString());

            // Récupération des messages
            List<MessageDTO> messageDTOs = article.getMessages().stream()
                    .map(message -> {
                        MessageDTO messageDTO = new MessageDTO();
                        messageDTO.setId(message.getId());
                        messageDTO.setUserUsername(message.getUser().getUsername());
                        messageDTO.setMessage(message.getMessage());
                        return messageDTO;
                    })
                    .collect(Collectors.toList());
            articleWithMessagesDTO.setMessages(messageDTOs);
            return new ResponseEntity<>(articleWithMessagesDTO, HttpStatus.OK); // Retourne l'article avec les messages
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Retourne 404 Not Found si l'article n'existe pas
        }
    }

    // Méthode pour sauvegarder un message lié à un article
    @PostMapping("/articles/{articleId}/messages")
    public ResponseEntity<PostMessagesDto> saveMessages(@RequestHeader("Authorization") String authorizationHeader,
            @PathVariable Long articleId,
            @RequestBody RequestMessagesDTO message) {
        // Extraction du token d'authentification du header
        String token = authorizationHeader.substring("Bearer ".length()).trim();
        User user = userService.getUserByToken(token); // Récupération de l'utilisateur à partir du token

        Optional<Article> articleOptional = articleService.getArticleById(articleId);
        if (articleOptional.isPresent()) {
            Article article = articleOptional.get();

            // Initialiser la collection des messages si elle est nulle
            if (article.getMessages() == null) {
                article.setMessages(new HashSet<>()); // Assurez-vous que c'est un Set
            }

            // Création et ajout du nouveau message
            Messages newMessage = new Messages();
            newMessage.setUser(user);
            newMessage.setMessage(message.getMessage());
            article.getMessages().add(newMessage);

            articleService.saveArticles(article); // Enregistrement de l'article avec le nouveau message

            PostMessagesDto postMessagesDto = new PostMessagesDto();
            postMessagesDto.setMessage(message.getMessage());
            postMessagesDto.setArticle_id(article.getId());
            postMessagesDto.setUser_id(user.getId());

            return new ResponseEntity<>(postMessagesDto, HttpStatus.OK); // Retourne les détails du message sauvegardé
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Article non trouvé
        }
    }

    // Méthode pour créer un nouvel article
    @PostMapping(value = "/articles")
    @ApiOperation(value = "Create a new Article", notes = "Creates a new Article.")
    public ResponseEntity<Article> saveRentals(@RequestHeader("Authorization") String authorizationHeader,
            @RequestBody ArticleRequestDto articleDto) {
        // Extraction du token d'authentification du header
        String token = authorizationHeader.substring("Bearer ".length()).trim();

        // Récupération de l'utilisateur à partir du token
        User user = userService.getUserByToken(token);

        // Création d'une nouvelle instance d'article
        Long themeId = articleDto.getTheme();
        Themes themeIdObject = themesService.getThemesById(themeId);
        Article article = new Article();
        article.setTitle(articleDto.getTitle());
        article.setDescription(articleDto.getDescription());
        article.setAuthor(user); // Attribution de l'auteur à l'article
        article.setTheme(themeIdObject);

        // Enregistrement de l'article via le service
        Article savedRentals = articleService.saveArticles(article);

        if (savedRentals != null) {
            return new ResponseEntity<>(savedRentals, HttpStatus.CREATED); // Retourne 201 Created si l'article est sauvegardé
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); // Retournez un statut d'erreur approprié
        }
    }

    // Méthode pour récupérer des articles par thème
    @GetMapping("/articles/theme/{themeId}")
    public List<Article> getArticlesByTheme(@PathVariable Long themeId) {
        Themes theme = themesService.getThemesById(themeId); // Récupération du thème par son ID
        return articleService.getArticlesByTheme(theme); // Retourne la liste des articles associés au thème
    }
}
