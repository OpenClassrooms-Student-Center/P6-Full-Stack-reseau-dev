package com.openclassrooms.mddapi.service;

// Importation des classes nécessaires
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.mddapi.dto.ArticleMessagesDTO;
import com.openclassrooms.mddapi.dto.MessageDTO;
import com.openclassrooms.mddapi.model.Article;
import com.openclassrooms.mddapi.model.Themes;
import com.openclassrooms.mddapi.repository.ArticleRepository;

// Annotation pour indiquer que cette classe est un service Spring
@Service
public class ArticleService {

    // Injection de dépendance pour le dépôt d'articles
    @Autowired
    private ArticleRepository articleRepository;

    // Classe d'exception personnalisée pour gérer les articles non trouvés
    public class NotFoundException extends RuntimeException implements Serializable {

        private static final long serialVersionUID = 1L; // Version de la classe pour la sérialisation

        // Constructeur de l'exception
        public NotFoundException(String message) {
            super(message);
        }
    }

    // Méthode pour récupérer tous les articles
    public Iterable<ArticleMessagesDTO> getArticles() {
        // Récupère tous les articles de la base de données
        Iterable<Article> articles = articleRepository.findAll();

        // Convertit chaque article en ArticleMessagesDTO
        List<ArticleMessagesDTO> articleMessagesDTOs = ((Collection<Article>) articles).stream().map(article -> {
            ArticleMessagesDTO articleMessagesDTO = new ArticleMessagesDTO();
            // Remplit les informations de l'article dans l'objet DTO
            articleMessagesDTO.setArticleId(article.getId());
            articleMessagesDTO.setTitle(article.getTitle());
            articleMessagesDTO.setDescription(article.getDescription());
            articleMessagesDTO.setUsername(article.getAuthor().getUsername());
            articleMessagesDTO.setCreatedAt(article.getCreatedAt() != null ? article.getCreatedAt().toString() : null);
            articleMessagesDTO.setUpdatedAt(article.getUpdatedAt() != null ? article.getUpdatedAt().toString() : null);

            // Récupère et convertit les messages associés à l'article
            articleMessagesDTO.setMessages(article.getMessages().stream().map(message -> {
                MessageDTO messageDTO = new MessageDTO();
                messageDTO.setMessage(message.getMessage());
                messageDTO.setUserUsername(article.getAuthor().getUsername());
                messageDTO.setId(article.getMessages().stream().findFirst().get().getId());
                return messageDTO;
            }).collect(Collectors.toList()));
            return articleMessagesDTO;
        }).collect(Collectors.toList());

        return articleMessagesDTOs; // Retourne la liste des DTO d'articles
    }

    // Méthode pour récupérer un article par son ID
    public Optional<Article> getArticleById(Long id) {
        // Appel du dépôt pour trouver un article par ID
        return articleRepository.findById(id);
    }

    // Méthode pour sauvegarder un nouvel article
    public Article saveArticles(Article articles) {
        // Définit la date de création à l'heure actuelle
        articles.setCreatedAt(java.time.LocalDateTime.now());
        return articleRepository.save(articles); // Sauvegarde l'article
    }

    // Méthode pour mettre à jour un article existant
    public Article updateArticles(Article updatedRentals) {
        // Recherche de l'article existant par ID
        Article existingArticle = articleRepository.findById(updatedRentals.getId()).orElse(null);

        // Vérifie si l'article existe
        if (existingArticle != null) {
            // Met à jour les attributs de l'article
            existingArticle.setTitle(updatedRentals.getTitle());
            existingArticle.setDescription(updatedRentals.getDescription());
            existingArticle.setUpdatedAt(LocalDateTime.now()); // Met à jour la date de mise à jour
            existingArticle.setAuthor(updatedRentals.getAuthor());
            existingArticle.setTheme(updatedRentals.getTheme());

            return articleRepository.save(existingArticle); // Retourne l'article mis à jour
        } else {
            // Lève une exception si l'article n'est pas trouvé
            throw new NotFoundException("Enregistrement introuvable");
        }
    }

    // Méthode pour supprimer un article
    public void deleteArticles(Long articleId) {
        // Recherche de l'article existant par ID
        Article existingArticles = articleRepository.findById(articleId).orElse(null);

        // Vérifie si l'article existe
        if (existingArticles != null) {
            articleRepository.delete(existingArticles); // Supprime l'article
        } else {
            // Lève une exception si l'article n'est pas trouvé
            throw new NotFoundException("Enregistrement introuvable");
        }
    }

    // Méthode pour récupérer les articles par thème
    public List<Article> getArticlesByTheme(Themes theme) {
        return articleRepository.findByTheme(theme); // Retourne la liste des articles associés à un thème
    }
}
