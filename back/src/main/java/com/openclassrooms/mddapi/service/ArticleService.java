package com.openclassrooms.mddapi.service;

// Importation des classes nécessaires
import java.io.Serializable;
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
import com.openclassrooms.mddapi.repository.ArticleRepository; // Import the missing MessageDTO class

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
        Iterable<Article> articles = articleRepository.findAll();
        List<ArticleMessagesDTO> articleMessagesDTOs = ((Collection<Article>) articles).stream().map(article -> {
            ArticleMessagesDTO articleMessagesDTO = new ArticleMessagesDTO();
            articleMessagesDTO.setArticleId(article.getId());
            articleMessagesDTO.setTitle(article.getTitle());
            articleMessagesDTO.setDescription(article.getDescription());
            articleMessagesDTO.setArticleId(article.getId());
            articleMessagesDTO.setUsername(article.getAuthor().getUsername());
            articleMessagesDTO.setMessages(article.getMessages().stream().map(message -> {
                MessageDTO messageDTO = new MessageDTO(); 
                messageDTO.setMessage(message.getMessage());
                messageDTO.setUserUsername(article.getAuthor().getUsername());
                messageDTO.setId(article.getMessages().stream().findFirst().get().getId());
                return messageDTO;
            }).collect(Collectors.toList()));
            return articleMessagesDTO;
        }).collect(Collectors.toList());
        return articleMessagesDTOs;
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
        // Appel du dépôt pour sauvegarder l'article
        Article savedRentals = articleRepository.save(articles);
        return savedRentals; // Retourne l'article sauvegardé
    }

    // Méthode pour mettre à jour un article existant
    public Article updateArticles(Article updatedRentals) {
        // Recherche de l'article existant par ID
        Article existingArticles= articleRepository.findById(updatedRentals.getId()).orElse(null);
        
        // Vérifie si l'article existe
        if (existingArticles != null) {
            // Met à jour les attributs de l'article
            existingArticles.setTitle(updatedRentals.getTitle());
            existingArticles.setDescription(updatedRentals.getDescription());
            existingArticles.setUpdatedAt(updatedRentals.getUpdatedAt());
            existingArticles.setAuthor(updatedRentals.getAuthor());
            existingArticles.setTheme(updatedRentals.getTheme());
            // Sauvegarde les modifications
            Article updatedRecord = articleRepository.save(existingArticles);
            return updatedRecord; // Retourne l'article mis à jour
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
            // Supprime l'article
            articleRepository.delete(existingArticles);
        } else {
            // Lève une exception si l'article n'est pas trouvé
            throw new NotFoundException("Enregistrement introuvable");
        }
    }
    public List<Article> getArticlesByTheme(Themes theme) {
        return articleRepository.findByTheme(theme);
    }
}
