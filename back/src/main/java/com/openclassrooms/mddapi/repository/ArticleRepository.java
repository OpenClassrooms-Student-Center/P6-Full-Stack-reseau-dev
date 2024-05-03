package com.openclassrooms.mddapi.repository;

import com.openclassrooms.mddapi.models.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {

    List<Article> findByTheme_ThemeId(Long theme_id);
    //Failed to create query for method public abstract java.util.List com.openclassrooms.mddapi.
    // repository.ArticleRepository.findByThemeTheme_id(java.lang.Long)!
    // No property 'theme' found for type 'Theme' Traversed path: Article.theme.


}
