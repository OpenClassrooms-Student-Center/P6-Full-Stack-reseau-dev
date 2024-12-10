package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.configuration.ArticleMapper;
import com.openclassrooms.mddapi.configuration.CommentMapper;
import com.openclassrooms.mddapi.configuration.ThemeMapper;
import com.openclassrooms.mddapi.dto.*;
import com.openclassrooms.mddapi.model.ArticleModel;
import com.openclassrooms.mddapi.model.CommentModel;
import com.openclassrooms.mddapi.model.ThemeModel;
import com.openclassrooms.mddapi.model.UserModel;
import com.openclassrooms.mddapi.repository.ArticleRepository;
import com.openclassrooms.mddapi.repository.ThemeRepository;
import com.openclassrooms.mddapi.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ArticleService {
    @Autowired private ArticleRepository articleRepository;
    @Autowired private ThemeRepository themeRepository;
    @Autowired private UserRepository userRepository;
    @Autowired @Qualifier("customModelMapper") private ModelMapper modelMapper;
    /**
     * Saves a new article based on the provided data and user email.
     *
     * @param articleCreateDto The DTO containing the data for creating the article.
     * @param email The email of the user creating the article.
     * @return A ResponseEntity containing the result of the article save operation.
     */
    public ResponseEntity<?> saveArticle(ArticleCreateDto articleCreateDto, String email) {
        UserModel author = userRepository.findByEmail(email);
        Optional<ThemeModel> theme = themeRepository.findById(articleCreateDto.getThemeId());

        if (!theme.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Theme not found");
        }

        ArticleModel article = modelMapper.map(articleCreateDto, ArticleModel.class);
        article.setAuthor(author);
        article.setTheme(theme.get());
        article.setComments(new ArrayList<>());
        article.setPublishedDate(new Date());

        ArticleModel newArticle = articleRepository.save(article);

        if (newArticle != null) {
            ArticleDto articleDto = ArticleMapper.toDTO(newArticle);
            return ResponseEntity.ok().body(articleDto);
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("La sauvegarde a échoué");
        }
    }
    /**
     * Retrieves a single article by its ID.
     *
     * @param id The ID of the article to retrieve.
     * @return A ResponseEntity containing the article or a 404 status if not found.
     */
    public ResponseEntity<?> singleArticle(Long id) {
        Optional<ArticleModel> article = articleRepository.findById(id);
        if (article.isPresent()) {
            ArticleDto articleDto = ArticleMapper.toDTO(article.get());
            return ResponseEntity.ok().body(articleDto);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Article not found");
        }
    }
    /**
     * Retrieves a list of articles based on the user's subscribed themes.
     *
     * @param email The email of the user requesting the articles.
     * @param reverse Whether the list should be sorted in reverse order by published date.
     * @return A ResponseEntity containing the list of articles.
     */
    public ResponseEntity<?> listArticles(String email, Boolean reverse) {
        UserModel user = userRepository.findByEmail(email);

        Set<ThemeModel> userThemesSet = user.getThemes();
        List<ThemeModel> userThemes = new ArrayList<>(userThemesSet);

        List<ArticleModel> articles = articleRepository.findByThemeIn(userThemes);
        articles.sort(Comparator.comparing(ArticleModel::getPublishedDate));
        if(reverse){
            Collections.reverse(articles);
        }
        List<ArticleDto> articleDTOs = articles.stream()
                .map(ArticleMapper::toDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(articleDTOs);
    }
    /**
     * Retrieves a list of all available themes, marking those the user is subscribed to.
     *
     * @param email The email of the user requesting the themes.
     * @return A ResponseEntity containing the list of themes.
     */
    public ResponseEntity<?> listThemes(String email) {
        UserModel user = userRepository.findByEmail(email);
        List<ThemeModel> themes = themeRepository.findAll();

        List<ThemeDto> themeDTOs = themes.stream().map(theme -> {
            ThemeDto dto = ThemeMapper.toDTO(theme);
            dto.setSubscribed(user.getThemes().contains(theme));
            return dto;
        }).collect(Collectors.toList());

        return ResponseEntity.ok().body(themeDTOs);
    }
    /**
     * Subscribes a user to a specific theme.
     *
     * @param email The email of the user subscribing to the theme.
     * @param themeId The ID of the theme to subscribe to.
     * @return A ResponseEntity containing the subscribed theme DTO.
     */
    public ResponseEntity<ThemeDto> subscribeToTheme(String email, Long themeId) {
        UserModel user = userRepository.findByEmail(email);
        Optional<ThemeModel> themeOpt = themeRepository.findById(themeId);

        ThemeModel theme = themeOpt.get();
        user.getThemes().add(theme);
        userRepository.save(user);

        ThemeDto themeDto = ThemeMapper.toDTO(theme);
        themeDto.setSubscribed(true);

        return ResponseEntity.ok(themeDto);
    }
    /**
     * Unsubscribes a user from a specific theme.
     *
     * @param email The email of the user unsubscribing from the theme.
     * @param themeId The ID of the theme to unsubscribe from.
     * @return A ResponseEntity containing the unsubscribed theme DTO.
     */
    public ResponseEntity<ThemeDto> unsubscribeToTheme(String email, Long themeId) {
        UserModel user = userRepository.findByEmail(email);
        Optional<ThemeModel> themeOpt = themeRepository.findById(themeId);

        ThemeModel theme = themeOpt.get();
        user.getThemes().remove(theme);
        userRepository.save(user);

        ThemeDto themeDto = ThemeMapper.toDTO(theme);
        themeDto.setSubscribed(false);

        return ResponseEntity.ok(themeDto);
    }
    /**
     * Adds a comment to an article.
     *
     * @param articleId The ID of the article to comment on.
     * @param email The email of the user adding the comment.
     * @param commentDto The DTO containing the comment data.
     * @return A ResponseEntity containing the updated article or a 404 status if the article or user is not found.
     */
    public ResponseEntity<?> addComment(Long articleId, String email, CommentDto commentDto) {
        Optional<ArticleModel> articleOpt = articleRepository.findById(articleId);
        UserModel user = userRepository.findByEmail(email);
        if (articleOpt.isPresent()) {
            CommentModel comment = CommentMapper.toModel(commentDto, articleOpt.get(), user);
            articleOpt.get().getComments().add(comment);
            articleRepository.save(articleOpt.get());
            return singleArticle(articleOpt.get().getId());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Article or User not found");
        }
    }

}
