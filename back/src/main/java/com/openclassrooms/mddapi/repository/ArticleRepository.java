package com.openclassrooms.mddapi.repository;

import com.openclassrooms.mddapi.model.ArticleModel;
import com.openclassrooms.mddapi.model.ThemeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<ArticleModel, Long> {
    List<ArticleModel> findByThemeIn(List<ThemeModel> themes);
}
