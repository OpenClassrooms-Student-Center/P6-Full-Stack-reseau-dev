package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.dto.ArticleDto;
import com.openclassrooms.mddapi.models.Article;
import com.openclassrooms.mddapi.models.Theme;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.services.ArticleService;
import com.openclassrooms.mddapi.services.ThemeService;
import com.openclassrooms.mddapi.services.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.chrono.ChronoLocalDateTime;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/article")
@Log4j2
public class ArticleController {

    @Autowired
    ArticleService articleService;

    @Autowired
    ThemeService themeService;

    @Autowired
    UserService userService;

    @GetMapping()
    public ResponseEntity<?> findAll() {
        List<Article> articleList = this.articleService.findAll();
        List<ArticleDto> articleDtoList= new ArrayList<>();

        for(Article article : articleList) {
            ArticleDto articleDto= new ArticleDto();

            articleDtoList.add(articleDto);
        }

        return ResponseEntity.ok().body(articleDtoList);
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody ArticleDto articleDto ) {

        Article article = new Article();
        article.setTitre(articleDto.getTitre());
        article.setContenu(articleDto.getDescription());

        Theme theme = new Theme();
        if( articleDto.getTheme_id() != null) {
            theme= themeService.findById(articleDto.getTheme_id());
        }

        article.setTheme(theme);
        article.setCreatedAt(articleDto.getDate());

        User user = new User();
        if( articleDto.getUser_id() != null) {
            user = userService.findById(articleDto.getUser_id());
        }
        article.setAuteur(user.getFirstName());

        return ResponseEntity.ok().body(article);
    }
}
