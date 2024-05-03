package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.models.Article;
import com.openclassrooms.mddapi.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    public List<Article> findAll() {
        return this.articleRepository.findAll();
    }

    public Article findById(Long id) {
        return this.articleRepository.findById(id).orElse(null);
    }

    public Article addArticle(Article article) {
        return this.articleRepository.save(article);
    }
}
