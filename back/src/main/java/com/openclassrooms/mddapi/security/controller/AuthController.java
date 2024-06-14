package com.openclassrooms.mddapi.security.controller;

import com.openclassrooms.mddapi.dtos.requests.RefreshTokenRequest;
import com.openclassrooms.mddapi.dtos.requests.RegisterRequest;
import com.openclassrooms.mddapi.dtos.responses.AuthInfoResponse;
import com.openclassrooms.mddapi.dtos.responses.AuthRefreshResponse;
import com.openclassrooms.mddapi.dtos.responses.MessageResponse;
import com.openclassrooms.mddapi.exceptions.BadRequestExceptionHandler;
import com.openclassrooms.mddapi.exceptions.ForbidenExceptionHandler;
import com.openclassrooms.mddapi.model.MddUser;
import com.openclassrooms.mddapi.model.RefreshToken;
import com.openclassrooms.mddapi.security.services.RefreshTokenService;
import com.openclassrooms.mddapi.security.services.TokenService;
import com.openclassrooms.mddapi.service.MddUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.regex.Pattern;

import static com.openclassrooms.mddapi.helper.PasswordValidation.validatePassword;

/**
 * API controller for handling authentication requests.
 */
@RestController
@Slf4j
@SecurityScheme(
        name = "basicAuth", // can be set to anything
        type = SecuritySchemeType.HTTP,
        scheme = "basic"
)
public class AuthController {

    private final TokenService tokenService;

    private final RefreshTokenService refreshTokenService;

    private final MddUserService userService;

    @Autowired
    PasswordEncoder passwordEncoder;

    /**
     * Constructor for AuthController.
     *
     * @param tokenService the JWT token service
     * @param refreshTokenService the refresh token service
     * @param userService the user service
     */
    public AuthController(TokenService tokenService, RefreshTokenService refreshTokenService, MddUserService userService) {
        this.tokenService = tokenService;
        this.refreshTokenService = refreshTokenService;
        this.userService = userService;
    }


    /**
     * Endpoint for generating a JWT token.
     *
     * @param authentication the Authentication object containing the user's credentials
     * @return ResponseEntity with authorization information
     */
    @Operation(summary = "Get JWT token", description = "Get JWT token")
    @SecurityRequirement(name = "basicAuth")
    @PostMapping("/token")
    public ResponseEntity<AuthInfoResponse> token(Authentication authentication) {
        log.info("Token requested for user : " + authentication.getName());
        String token = tokenService.generateTokenFromAuthentication(authentication);
        String refreshToken = refreshTokenService.createRefreshToken(authentication.getName()).getToken();
        log.info("Token granted ");

        return ResponseEntity.ok(new AuthInfoResponse(token, refreshToken));
    }

    /**
     * Endpoint for refreshing a JWT token.
     *
     * @param refreshTokenRequest the RefreshTokenRequest containing the old refresh token
     * @return ResponseEntity with the new authorization information
     */
    @PostMapping("/refreshtoken")
    public ResponseEntity<AuthRefreshResponse> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        RefreshToken refreshToken = refreshTokenService.findByToken(refreshTokenRequest.getToken())
                .orElseThrow(() ->new ForbidenExceptionHandler("No Refresh Token"));

        MddUser user = refreshToken.getUserInfo();
        log.info("Token refreshment requested for user : " + user.getEmail());

        refreshTokenService.verifyExpiration(refreshToken);
        String token = tokenService.generateTokenFromUsername(user.getEmail());
        log.info("Token refreshment granted ");
        return ResponseEntity.ok(new AuthRefreshResponse(token));
    }

    /**
     * Endpoint for registering a new user.
     *
     * @param registerRequest the RegisterRequest containing the user's registration information
     * @return ResponseEntity with a message indicating the status of the registration
     */
    @PostMapping("/register")
    public ResponseEntity<MessageResponse> register(@RequestBody RegisterRequest registerRequest) {
        if (!validatePassword(registerRequest.getPassword())) {
          throw new BadRequestExceptionHandler("The password is not valid");
        }
        MddUser mddUser = new MddUser();
        mddUser.setEmail(registerRequest.getMail());

        mddUser.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        mddUser.setUsername(registerRequest.getUsername());
        MddUser user = userService.createUser(mddUser);
        log.info("User registered with user name: " + user.getUsername());
        return ResponseEntity.ok(new MessageResponse("User registered successfully"));
    }
}