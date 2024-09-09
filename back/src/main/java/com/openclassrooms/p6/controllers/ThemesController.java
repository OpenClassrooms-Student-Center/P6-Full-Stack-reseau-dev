package com.openclassrooms.p6.controllers;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.p6.exception.ApiException;
import com.openclassrooms.p6.exception.GlobalExceptionHandler;
import com.openclassrooms.p6.mapper.SubscriptionMapper;
import com.openclassrooms.p6.mapper.ThemeMapper;
import com.openclassrooms.p6.model.Subscriptions;
import com.openclassrooms.p6.model.Themes;
import com.openclassrooms.p6.model.Users;
import com.openclassrooms.p6.payload.response.MessageResponse;
import com.openclassrooms.p6.payload.response.SingleThemeResponse;
import com.openclassrooms.p6.payload.response.SingleThemeSubscriptionResponse;
import com.openclassrooms.p6.service.SubscriptionsService;
import com.openclassrooms.p6.service.ThemeService;
import com.openclassrooms.p6.service.UserService;
import com.openclassrooms.p6.utils.JwtUtil;

/**
 * This class is the controller for managing themes in the API.
 * It handles requests related to themes, such as retrieving all themes,
 * retrieving subscribed themes, subscribing to a theme, and unsubscribing from
 * a theme.
 * The class is annotated with {@code @RestController} and
 * {@code @RequestMapping} to define the
 * base URL for all theme-related endpoints.
 * It also has the {@code @CrossOrigin} annotation to allow cross-origin
 * requests from
 * any domain.
 */
@CrossOrigin("*")
@RestController
@RequestMapping("/api/themes")
public class ThemesController {

    /**
     * {@link ThemeService} for theme-related operations.
     */
    @Autowired
    private ThemeService themeService;

    /**
     * {@link SubscriptionsService} for subscription-related operations.
     */
    @Autowired
    private SubscriptionsService subscriptionsService;

    @Autowired
    private SubscriptionMapper subscriptionsMapper;

    /**
     * {@link ThemeMapper} for converting between entity and DTO types.
     */
    @Autowired
    private ThemeMapper themeMapper;

    /**
     * {@link UserService} for user-related operations.
     */
    @Autowired
    private UserService userService;

    /**
     * Retrieves all themes and their subscription status for a specific user.
     *
     * @param authorizationHeader The authorization header containing the JWT token.
     * @return A ResponseEntity containing the response body with the list of themes
     *         and their subscription status.
     * @throws ApiException if there is an error while retrieving the themes or
     *                      verifying the user.
     */
    @GetMapping("")
    public ResponseEntity<?> getAllThemes(@RequestHeader("Authorization") String authorizationHeader) {
        try {
            verifyUserValidityFromToken(authorizationHeader);

            List<Themes> themesEntityList = themeService.getThemes();

            Iterable<SingleThemeResponse> themesDto = (themeMapper.toDtoThemes(themesEntityList));

            return ResponseEntity.status(HttpStatus.OK).body(themesDto);
        } catch (ApiException e) {
            return GlobalExceptionHandler.handleApiException(e);
        }
    }

    /**
     * Retrieves all subscribed themes for a specific user.
     *
     * @param authorizationHeader The authorization header containing the JWT token.
     * @return A ResponseEntity containing the response body with the list of
     *         subscribed themes.
     * @throws ApiException if there is an error while retrieving the subscribed
     *                      themes or verifying the user.
     */
    @GetMapping("/subscribed")
    public ResponseEntity<?> getSubscribedThemes(@RequestHeader("Authorization") String authorizationHeader) {
        try {
            Long userId = verifyUserValidityFromToken(authorizationHeader);

            Iterable<Subscriptions> subscriptions = subscriptionsService.findAllUserSubscriptions(userId);

            Iterable<SingleThemeSubscriptionResponse> subscriptionsDto = subscriptionsMapper
                    .toDtoSubscriptions(subscriptions);

            return ResponseEntity.status(HttpStatus.OK).body(subscriptionsDto);
        } catch (ApiException e) {
            return GlobalExceptionHandler.handleApiException(e);
        }
    }

    @PostMapping("/subscribe/")
    public ResponseEntity<?> subscribe(
            @RequestParam final Long themeId,
            @RequestHeader("Authorization") String authorizationHeader) {
        try {
            Long userId = verifyUserValidityFromToken(authorizationHeader);

            verifyAndGetThemeById(themeId);

            Subscriptions subscription = getUserThemeSubscription(userId, themeId);

            Boolean subscriptionDoesNotExistYet = subscription == null;
            if (subscriptionDoesNotExistYet) {
                subscriptionsService.createSubscription(userId, themeId);
                MessageResponse response = new MessageResponse("Successfully subscribed to the theme!");

                return ResponseEntity.status(HttpStatus.CREATED).body(response);
            }

            Boolean userIsAlreadySubscribedToTheme = subscription.getIsSubscribed();
            if (userIsAlreadySubscribedToTheme) {
                MessageResponse response = new MessageResponse("You are already subscribed to this theme!");

                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            subscriptionsService.updateThemeSubscription(subscription, true);

            MessageResponse response = new MessageResponse(
                    "Successfully subscribed to the theme!");

            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (ApiException e) {
            return GlobalExceptionHandler.handleApiException(e);
        }
    }

    @PostMapping("/unsubscribe/")
    public ResponseEntity<?> unsubscribe(
            @RequestParam final Long themeId,
            @RequestHeader("Authorization") String authorizationHeader) {
        try {
            Long userId = verifyUserValidityFromToken(authorizationHeader);

            verifyAndGetThemeById(themeId);

            Subscriptions subscription = getUserThemeSubscription(userId, themeId);

            Boolean subscriptionDoesNotExistYet = subscription == null;
            if (subscriptionDoesNotExistYet) {
                MessageResponse response = new MessageResponse("Subscription does not exist!");

                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

            Boolean userIsAlreadyUnsubscribedToTheme = !subscription.getIsSubscribed();
            if (userIsAlreadyUnsubscribedToTheme) {
                MessageResponse response = new MessageResponse(
                        "You cannot unsubscribe to a theme that you're not subscribed to !");

                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            subscriptionsService.updateThemeSubscription(subscription, false);
            MessageResponse response = new MessageResponse("Successfully unsubscribed to the theme !");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (ApiException e) {
            return GlobalExceptionHandler.handleApiException(e);
        }
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
     * Retrieves a user by their ID and verifies their existence.
     *
     * @param userId The ID of the user to retrieve.
     * @return The user with the given ID.
     * @throws ApiException if the user with the given ID does not exist.
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
     * Retrieves the subscription of a user to a specific theme.
     *
     * @param userId  The ID of the user.
     * @param themeId The ID of the theme.
     * @return The subscription of the user to the theme.
     */
    private Subscriptions getUserThemeSubscription(Long userId, Long themeId) {

        Iterable<Subscriptions> subscriptions = subscriptionsService.findAllUserSubscriptions(userId);

        Subscriptions subscription = StreamSupport.stream(subscriptions.spliterator(), false)
                .filter(s -> s.getThemeId().equals(themeId))
                .findFirst()
                .orElse(null);

        return subscription;
    }
}
