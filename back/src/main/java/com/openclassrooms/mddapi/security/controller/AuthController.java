package com.openclassrooms.mddapi.security.controller;

import com.openclassrooms.mddapi.dtos.requests.RefreshTokenRequest;
import com.openclassrooms.mddapi.dtos.responses.AuthInfo;
import com.openclassrooms.mddapi.model.MddUser;
import com.openclassrooms.mddapi.model.RefreshToken;
import com.openclassrooms.mddapi.security.services.RefreshTokenService;
import com.openclassrooms.mddapi.security.services.TokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class AuthController {

    private final TokenService tokenService;

    private final RefreshTokenService refreshTokenService;

    public AuthController(TokenService tokenService, RefreshTokenService refreshTokenService) {
        this.tokenService = tokenService;
        this.refreshTokenService = refreshTokenService;
    }

    @PostMapping("/token")
    public ResponseEntity<AuthInfo> token(Authentication authentication) {
        log.debug("Token requested for user : " + authentication.getName());
        String token = tokenService.generateTokenFromAuthentication(authentication);
        String refreshToken = refreshTokenService.createRefreshToken(authentication.getName()).getToken();
        log.debug("Token granted ");

        return ResponseEntity.ok(new AuthInfo(token, refreshToken));
    }

    @PostMapping("/refreshtoken")
    public String refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        RefreshToken refreshToken = refreshTokenService.findByToken(refreshTokenRequest.getToken())
                .orElseThrow(() ->new RuntimeException("Refresh Token is not in DB..!!"));

        MddUser user = refreshToken.getUserInfo();
        log.debug("Token refreshment requested for user : " + user.getUsername());

        refreshTokenService.verifyExpiration(refreshToken);
        String token = tokenService.generateTokenFromUsername(user.getUsername());
        log.debug("Token refreshment granted ");

        return token;
    }
}
