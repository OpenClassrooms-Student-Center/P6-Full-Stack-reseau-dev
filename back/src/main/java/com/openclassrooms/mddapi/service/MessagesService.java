package com.openclassrooms.mddapi.service;

import java.io.Serializable;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.mddapi.dto.PostMessagesDto;
import com.openclassrooms.mddapi.model.Article;
import com.openclassrooms.mddapi.model.Messages;


@Service
public class MessagesService {

  

    @Autowired
    private ArticleService articleService;

    public class NotFoundException extends RuntimeException implements Serializable {
        private static final long serialVersionUID = 1L;
        public NotFoundException(String message) {
            super(message);
        }
    }

    // get message by article_id
    public Optional<PostMessagesDto> getMessageByArticleId(Long ArticleId) {
        Article article = articleService.getArticleById(ArticleId).orElse(null);    
        Set<Messages> message = article.getMessages();
        PostMessagesDto postMessagesDto = new PostMessagesDto();
        postMessagesDto.setMessage(message.stream().findFirst().get().getMessage());
        postMessagesDto.setArticle_id(ArticleId);
        postMessagesDto.setUser_id(message.stream().findFirst().get().getUser().getId());
        return Optional.of(postMessagesDto);
    } }