package com.openclassrooms.mddapi.security.controller;

import com.openclassrooms.mddapi.dtos.requests.RefreshTokenRequest;
import com.openclassrooms.mddapi.dtos.requests.RegisterRequest;
import com.openclassrooms.mddapi.dtos.responses.AuthInfoResponse;
import com.openclassrooms.mddapi.dtos.responses.AuthRefreshResponse;
import com.openclassrooms.mddapi.exceptions.ForbidenExceptionHandler;
import com.openclassrooms.mddapi.model.MddUser;
import com.openclassrooms.mddapi.model.RefreshToken;
import com.openclassrooms.mddapi.security.services.RefreshTokenService;
import com.openclassrooms.mddapi.security.services.TokenService;
import com.openclassrooms.mddapi.service.MddUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class AuthController {

    private final TokenService tokenService;

    private final RefreshTokenService refreshTokenService;

    private final MddUserService userService;

    @Autowired
    PasswordEncoder passwordEncoder;

    public AuthController(TokenService tokenService, RefreshTokenService refreshTokenService, MddUserService userService) {
        this.tokenService = tokenService;
        this.refreshTokenService = refreshTokenService;
        this.userService = userService;
    }

    @PostMapping("/token")
    public ResponseEntity<AuthInfoResponse> token(Authentication authentication) {
        log.info("Token requested for user : " + authentication.getName());
        String token = tokenService.generateTokenFromAuthentication(authentication);
        String refreshToken = refreshTokenService.createRefreshToken(authentication.getName()).getToken();
        log.info("Token granted ");

        return ResponseEntity.ok(new AuthInfoResponse(token, refreshToken));
    }

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

    @PostMapping("/register")
    public ResponseEntity<MddUser> register(@RequestBody RegisterRequest registerRequest) {
        MddUser mddUser = new MddUser();
        mddUser.setEmail(registerRequest.getMail());
        mddUser.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        mddUser.setUsername(registerRequest.getUsername());
        MddUser user = userService.createUser(mddUser);
        log.info("User registered with user name: " + user.getUsername());
        return ResponseEntity.ok(user);
    }
}