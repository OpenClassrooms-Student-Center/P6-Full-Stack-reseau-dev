package com.openclassrooms.p6.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.p6.exception.ApiException;
import com.openclassrooms.p6.exception.GlobalExceptionHandler;
import com.openclassrooms.p6.mapper.ArticleMapper;
import com.openclassrooms.p6.mapper.CommentMapper;
import com.openclassrooms.p6.model.Articles;
import com.openclassrooms.p6.model.Comments;
import com.openclassrooms.p6.model.Themes;
import com.openclassrooms.p6.model.Users;
import com.openclassrooms.p6.payload.request.ArticleRequest;
import com.openclassrooms.p6.payload.request.CommentRequest;
import com.openclassrooms.p6.payload.response.ArticleSummaryResponse;
import com.openclassrooms.p6.payload.response.CommentResponse;
import com.openclassrooms.p6.payload.response.MessageResponse;
import com.openclassrooms.p6.payload.response.MultipleArticlesResponse;
import com.openclassrooms.p6.payload.response.SingleArticleResponse;
import com.openclassrooms.p6.service.ArticleService;
import com.openclassrooms.p6.service.CommentsService;
import com.openclassrooms.p6.service.ThemeService;
import com.openclassrooms.p6.service.UserService;
import com.openclassrooms.p6.utils.JwtUtil;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * This is the ArticlesController class. It is a REST controller that handles
 * requests related to articles.
 * The class is annotated with {@code @CrossOrigin("*")} to allow cross-origin
 * requests,
 * and {@code @RestController} to indicate that it is a controller class.
 * The base URL for all the endpoints in this controller is "/api/articles", as
 * specified by the {@code @RequestMapping} annotation.
 */
@CrossOrigin("*")
@RestController
@RequestMapping("/api/articles")
public class ArticlesController {

    /**
     * UserService to manage user-related operations.
     */
    @Autowired
    private UserService userService;

    /**
     * ArticleService to manage article-related operations.
     */
    @Autowired
    private ArticleService articleService;

    /**
     * ArticleMapper for converting between entity and DTO types.
     */
    @Autowired
    private ArticleMapper articleMapper;

    /**
     * CommentsService to manage comments-related operations.
     */
    @Autowired
    private CommentsService commentsService;

    /**
     * CommentMapper for converting between entity and DTO types.
     */
    @Autowired
    private CommentMapper commentsMapper;

    /**
     * ThemeService to manage theme-related operations.
     */
    @Autowired
    private ThemeService themeService;

    /**
     * Registers a new user.
     *
     * @param request The registration request containing user details.
     * @return ResponseEntity<AuthResponse> A JWT if registration is successful.
     */
    @GetMapping("")
    public ResponseEntity<?> getAllArticles(@RequestHeader("Authorization") String authorizationHeader) {
        try {
            verifyUserValidityFromToken(authorizationHeader);

            List<Articles> articlesEntity = articleService.getArticles();

            Iterable<ArticleSummaryResponse> articlesDto = (articleMapper.toDtoArticles(articlesEntity));

            List<ArticleSummaryResponse> normalizedArticles = new ArrayList<>();

            normalizedArticles.addAll((List<? extends ArticleSummaryResponse>) articlesDto);

            MultipleArticlesResponse response = new MultipleArticlesResponse(normalizedArticles);

            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (ApiException e) {
            return GlobalExceptionHandler.handleApiException(e);
        }
    }

    /**
     * Retrieves an article by its ID.
     *
     * @param articleId           The ID of the article to retrieve.
     * @param authorizationHeader The authorization header containing the JWT token.
     * @return ResponseEntity<?> The response entity containing the result of the
     *         operation.
     * @throws ApiException if there is an error in the API.
     */
    @GetMapping("/")
    public ResponseEntity<?> getArticlesById(@RequestParam final Long articleId,
            @Valid @RequestHeader("Authorization") String authorizationHeader) {
        try {
            verifyUserValidityFromToken(authorizationHeader);

            Articles articleEntity = verifyAndGetArticlesById(articleId);
            ArticleSummaryResponse articleDto = articleMapper.toDtoArticle(articleEntity);

            String articleAuthor = getVerifiedUserById(articleEntity.getUserId()).getUsername();

            String theme = articleEntity.getTheme().getTitle();

            List<Comments> commentsEntityList = commentsService.getAllCommentsByArticleId(articleId);
            Iterable<CommentResponse> commentsDtoList = commentsMapper.toDtoComments(commentsEntityList);

            List<CommentResponse> normalizedComments = new ArrayList<>();

            normalizedComments.addAll((List<? extends CommentResponse>) commentsDtoList);

            SingleArticleResponse response = new SingleArticleResponse(articleId,
                    articleAuthor, articleDto.publicationDate(), theme, articleDto.title(), articleDto.description(),
                    normalizedComments);

            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (ApiException e) {
            return GlobalExceptionHandler.handleApiException(e);
        }
    }

    /**
     * Posts an article.
     *
     * @param request             The article request containing the article
     *                            details.
     * @param bindingResult       The BindingResult object that holds the validation
     *                            errors.
     * @param authorizationHeader The authorization header containing the JWT token.
     * @return ResponseEntity<?> The response entity containing the result of the
     *         operation.
     */
    @PostMapping("/")
    public ResponseEntity<?> postArticle(
            @RequestParam final Long themeId,
            @Valid @RequestBody ArticleRequest request, BindingResult bindingResult,
            @RequestHeader("Authorization") String authorizationHeader) {
        try {
            Long userId = verifyUserValidityFromToken(authorizationHeader);

            checkBodyPayloadErrors(bindingResult);

            verifyAndGetThemeById(themeId);

            articleService.createArticle(request, userId, themeId);

            MessageResponse response = new MessageResponse("Article has been successfully published !");

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (ApiException e) {
            return GlobalExceptionHandler.handleApiException(e);
        }
    }

    @PostMapping("/comment/")
    public ResponseEntity<?> postCommentToArticle(
            @RequestParam final Long articleId,
            @Valid @RequestBody CommentRequest request,
            BindingResult bindingResult,
            @RequestHeader("Authorization") String authorizationHeader) {
        try {
            checkBodyPayloadErrors(bindingResult);

            Long userId = verifyUserValidityFromToken(authorizationHeader);

            verifyAndGetArticlesById(articleId);

            commentsService.createComment(request, userId, articleId);

            MessageResponse response = new MessageResponse("Comment has been successfully published !");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (ApiException e) {
            return GlobalExceptionHandler.handleApiException(e);
        }
    }

    /**
     * Retrieves the user ID from the authorization header and checks that the user
     * exists
     *
     * @param authorizationHeader The authorization header containing the JWT token.
     * @return The user ID extracted from the JWT token.
     */
    private Long verifyUserValidityFromToken(String authorizationHeader) {
        String jwtToken = JwtUtil.extractJwtFromHeader(authorizationHeader);

        // Extract user ID from JWT
        Optional<Long> optionalUserIdFromToken = JwtUtil.extractUserId(jwtToken);

        Boolean hasJwtExtractionError = optionalUserIdFromToken.isEmpty();
        if (hasJwtExtractionError) {
            GlobalExceptionHandler.handleLogicError("Unauthorized", HttpStatus.UNAUTHORIZED);
        }

        Long userId = optionalUserIdFromToken.get();

        getVerifiedUserById(userId);

        return userId;
    }

    /**
     * Retrieves a user by their ID and verifies their existence.
     *
     * @param userId The ID of the user to retrieve.
     * @return The user with the given ID.
     * @throws ApiException if the user with the given ID does not exist.
     */
    private Users getVerifiedUserById(Long userId) {
        Optional<Users> optionalSpecificUser = userService.getUserById(userId);

        Boolean userWithIdDoesNotExist = optionalSpecificUser.isEmpty();
        if (userWithIdDoesNotExist) {
            GlobalExceptionHandler.handleLogicError("Not found",
                    HttpStatus.NOT_FOUND);
        }

        return optionalSpecificUser.get();
    }

    /**
     * Retrieves an article by its ID.
     *
     * @param articleId The ID of the article to retrieve.
     * @return The article with the given ID.
     * @throws ApiException if the article with the given ID does not exist.
     */
    private Articles verifyAndGetArticlesById(Long articleId) {
        Optional<Articles> optionalArticle = articleService.getArticleById(articleId);

        Boolean articleDoesNotExist = optionalArticle.isEmpty();
        if (articleDoesNotExist) {
            GlobalExceptionHandler.handleLogicError("Not found",
                    HttpStatus.NOT_FOUND);
        }
        return optionalArticle.get();
    }

    /**
     * Retrieves a theme by its ID and verifies its existence.
     *
     * @param themeId The ID of the theme to retrieve.
     * @return The theme with the given ID.
     * @throws ApiException if the theme with the given ID does not exist.
     */
    private Themes verifyAndGetThemeById(Long themeId) {
        Optional<Themes> optionalTheme = themeService.getThemeById(themeId);

        Boolean themeDoesNotExist = optionalTheme.isEmpty();
        if (themeDoesNotExist) {
            GlobalExceptionHandler.handleLogicError("Not found",
                    HttpStatus.NOT_FOUND);
        }

        return optionalTheme.get();
    }

    /**
     * Checks if there are any payload errors in the request body.
     *
     * @param bindingResult The BindingResult object that holds the validation
     *                      errors.
     */
    private void checkBodyPayloadErrors(BindingResult bindingResult) {
        Boolean payloadIsInvalid = bindingResult.hasErrors();
        if (payloadIsInvalid) {
            GlobalExceptionHandler.handlePayloadError("Bad request", bindingResult, HttpStatus.BAD_REQUEST);
        }
    }

}
