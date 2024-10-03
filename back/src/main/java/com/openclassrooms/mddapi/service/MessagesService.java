package com.openclassrooms.mddapi.service;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.mddapi.model.Article;
import com.openclassrooms.mddapi.model.Messages;
import com.openclassrooms.mddapi.repository.MessagesRepository;

@Service
public class MessagesService {

    @Autowired
    private MessagesRepository messagesRepository;

    @Autowired
    private ArticleService articleService;

    public class NotFoundException extends RuntimeException implements Serializable {
        private static final long serialVersionUID = 1L;
        public NotFoundException(String message) {
            super(message);
        }
    }

    // Récupérer tous les messages par article_id
    public Set<Messages> getMessagesByArticleId(Long articleId) {
        Article article = articleService.getArticleById(articleId).orElseThrow(
            () -> new NotFoundException("Article with ID " + articleId + " not found")
        );

        return article.getMessages();
    }

    // Enregistrer un message
    public Messages saveMessage(Messages message) {
        message.setCreatedAt(LocalDateTime.now());  // Set the current timestamp
        return messagesRepository.save(message);   // Save and return the message
    }
}
