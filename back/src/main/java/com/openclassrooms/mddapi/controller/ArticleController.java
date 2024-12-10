package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.dto.ArticleCreateDto;
import com.openclassrooms.mddapi.dto.CommentDto;
import com.openclassrooms.mddapi.service.ArticleService;
import com.openclassrooms.mddapi.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    ArticleService articleService;
    @Autowired
    JwtService jwtService;
    /**
     * Creates a new article using the provided data and the authenticated user's email.
     *
     * @param token The JWT token from the request header used to authenticate the user.
     * @param articleCreateDto The DTO containing the data for creating the article.
     * @return A ResponseEntity containing the created article or an error message if the creation fails.
     */
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestHeader("Authorization") String token, @RequestBody ArticleCreateDto articleCreateDto) {
        String email = jwtService.validateToken(token);
        return articleService.saveArticle(articleCreateDto, email);
    }
    /**
     * Retrieves a single article by its ID.
     *
     * @param token The JWT token from the request header used to authenticate the user.
     * @param idArticle The ID of the article to retrieve.
     * @return A ResponseEntity containing the article or a 404 status if not found.
     */
    @GetMapping("/single/{id}")
    public ResponseEntity<?> showArticle(@RequestHeader("Authorization") String token, @PathVariable("id") Long idArticle) {
        String email = jwtService.validateToken(token);
        return articleService.singleArticle(idArticle);
    }
    /**
     * Retrieves a list of articles based on the user's subscribed themes, with an option to sort in reverse order.
     *
     * @param token The JWT token from the request header used to authenticate the user.
     * @param reverse Whether to sort the articles in reverse chronological order.
     * @return A ResponseEntity containing the list of articles.
     */
    @GetMapping("/all")
    public ResponseEntity<?> listArticles(@RequestHeader("Authorization") String token, @RequestParam(value = "reverse", defaultValue = "false") boolean reverse) {
        String email = jwtService.validateToken(token);
        return articleService.listArticles(email, reverse);
    }
    /**
     * Retrieves a list of all available themes along with the user's subscription status for each theme.
     *
     * @param token The JWT token from the request header used to authenticate the user.
     * @return A ResponseEntity containing the list of themes and subscription statuses.
     */
    @GetMapping("/theme/all")
    public ResponseEntity<?> listThemeArticles(@RequestHeader("Authorization") String token) {
        String email = jwtService.validateToken(token);
        return articleService.listThemes(email);
    }
    /**
     * Subscribes the authenticated user to a specific theme.
     *
     * @param token The JWT token from the request header used to authenticate the user.
     * @param themeId The ID of the theme to subscribe to.
     * @return A ResponseEntity containing the subscribed theme or an error message.
     */
    @PostMapping("/subscribe/{themeId}")
    public ResponseEntity<?> subscribeToTheme(@RequestHeader("Authorization") String token, @PathVariable("themeId") Long themeId) {
        String email = jwtService.validateToken(token);
        return articleService.subscribeToTheme(email, themeId);
    }
    /**
     * Unsubscribes the authenticated user from a specific theme.
     *
     * @param token The JWT token from the request header used to authenticate the user.
     * @param themeId The ID of the theme to unsubscribe from.
     * @return A ResponseEntity containing the unsubscribed theme or an error message.
     */
    @PostMapping("/unsubscribe/{themeId}")
    public ResponseEntity<?> unsubscribeToTheme(@RequestHeader("Authorization") String token, @PathVariable("themeId") Long themeId) {
        String email = jwtService.validateToken(token);
        return articleService.unsubscribeToTheme(email, themeId);
    }
    /**
     * Adds a comment to a specific article by the authenticated user.
     *
     * @param token The JWT token from the request header used to authenticate the user.
     * @param articleId The ID of the article to comment on.
     * @param commentDto The DTO containing the comment data.
     * @return A ResponseEntity containing the updated article with the new comment.
     */
    @PostMapping("/{articleId}/comment")
    public ResponseEntity<?> addComment(@RequestHeader("Authorization") String token, @PathVariable Long articleId, @RequestBody CommentDto commentDto) {
        String email = jwtService.validateToken(token);
        return articleService.addComment(articleId, email, commentDto);
    }
}
