package com.openclassrooms.mddapi.service;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.mddapi.dto.PostMessagesDto;
import com.openclassrooms.mddapi.model.Article;
import com.openclassrooms.mddapi.model.Messages;
import com.openclassrooms.mddapi.repository.MessagesRepository;

// Annotation pour indiquer que cette classe est un service Spring
@Service
public class MessagesService {

    // Injection de dépendance pour le dépôt de messages
    @Autowired
    private MessagesRepository messagesRepository;

    // Injection de dépendance pour le service d'articles
    @Autowired
    private ArticleService articleService;

    // Classe d'exception personnalisée pour gérer les messages non trouvés
    public class NotFoundException extends RuntimeException implements Serializable {

        private static final long serialVersionUID = 1L; // Version de la classe pour la sérialisation

        // Constructeur de l'exception
        public NotFoundException(String message) {
            super(message);
        }
    }

    // Méthode pour récupérer un message par ID d'article
    public Optional<PostMessagesDto> getMessageByArticleId(Long ArticleId) {
        // Récupère l'article correspondant à l'ID donné
        Article article = articleService.getArticleById(ArticleId).orElse(null);

        // Récupère les messages associés à cet article
        Set<Messages> message = article.getMessages();

        // Crée un DTO pour le message à retourner
        PostMessagesDto postMessagesDto = new PostMessagesDto();
        postMessagesDto.setMessage(message.stream().findFirst().get().getMessage());
        postMessagesDto.setArticle_id(ArticleId);
        postMessagesDto.setUser_id(message.stream().findFirst().get().getUser().getId());

        // Retourne le DTO du message encapsulé dans un Optional
        return Optional.of(postMessagesDto);
    }

    // Méthode pour sauvegarder un message
    public Messages saveMessage(Messages message) {
        // Définit la date de création à l'heure actuelle
        message.setCreatedAt(LocalDateTime.now());

        // Sauvegarde le message dans le dépôt
        return messagesRepository.save(message);
    }

}
