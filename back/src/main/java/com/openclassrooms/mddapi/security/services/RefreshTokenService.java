package com.openclassrooms.mddapi.security.services;

import com.openclassrooms.mddapi.exceptions.ForbidenExceptionHandler;
import com.openclassrooms.mddapi.model.MddUser;
import com.openclassrooms.mddapi.model.RefreshToken;
import com.openclassrooms.mddapi.repository.RefreshTokenRepository;
import com.openclassrooms.mddapi.service.MddUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class RefreshTokenService {

    @Autowired
    RefreshTokenRepository refreshTokenRepository;

    @Autowired
    MddUserService userService;

    public RefreshToken createRefreshToken(String username){
        try {
            MddUser user = userService.findUserByEmail(username);
            refreshTokenRepository.findByUserInfoId(user.getId()).ifPresent(refreshToken
                    -> refreshTokenRepository.deleteById(refreshToken.getId()));
            RefreshToken refreshToken = RefreshToken.builder()
                    .userInfo(user)
                    .token(UUID.randomUUID().toString())
                    .expiryDate(Instant.now().plusSeconds(604800)) // set expiry of refresh token to 7 days
                    .build();
            return refreshTokenRepository.save(refreshToken);
        } catch (Exception e) {
            log.error("Error occurred while creating refresh token for user: " + username, e);
            throw new ForbidenExceptionHandler("Error occurred while creating refresh token.", e);
        }
    }



    public Optional<RefreshToken> findByToken(String token){
        return refreshTokenRepository.findByToken(token);
    }

    public RefreshToken verifyExpiration(RefreshToken token){
        if(token.getExpiryDate().compareTo(Instant.now())<0){
            refreshTokenRepository.delete(token);
            log.info("Refresh token is expired for user : " + token.getUserInfo().getUsername());
            throw new ForbidenExceptionHandler(token.getToken() + " Refresh token is expired. Please make a new login..!");
        }
        return token;
    }

    private void deleteRefreshToken(Long id){
        refreshTokenRepository.deleteById(id);
    }

}