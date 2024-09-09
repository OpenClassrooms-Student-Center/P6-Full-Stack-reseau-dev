package com.openclassrooms.p6.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.p6.model.Articles;
import com.openclassrooms.p6.payload.request.ArticleRequest;
import com.openclassrooms.p6.repository.ArticleRepository;

import lombok.Data;

/**
 * Service class for managing articles.
 */
@Data
@Service
public class ArticleService {

    /**
     * 
     * Articles repo to perform database operations on the {@link Articles} entity.
     */
    @Autowired
    private ArticleRepository articleRepository;

    /**
     * Retrieve all articles.
     *
     * @return A collection of all articles as a list.
     */
    public List<Articles> getArticles() {
        return articleRepository.findAll();
    }

    /**
     * Retrieves an article by its unique identifier.
     *
     * @param id The identifier of the article.
     * @return An Optional containing the article if found, or empty if not.
     */
    public Optional<Articles> getArticleById(final Long id) {
        return articleRepository.findById(id);
    }

    /**
     * Creates a new article
     *
     * @param rentalUpdateRequest The article request containing details.
     * @return The article created.
     */
    public Articles createArticle(ArticleRequest articleCreationRequest, Long userId, Long themeId) {
        Articles article = new Articles();

        article.setUserId(userId);
        article.setThemeId(themeId);
        article.setTitle(articleCreationRequest.title());
        article.setDescription(articleCreationRequest.description());

        return articleRepository.save(article);
    }

    /**
     * Deleted an article by its unique identifier.
     *
     * @param id The identifier of the article to be deleted.
     */
    public void deleteArticleById(final Long id) {
        articleRepository.deleteById(id);
    }

}
