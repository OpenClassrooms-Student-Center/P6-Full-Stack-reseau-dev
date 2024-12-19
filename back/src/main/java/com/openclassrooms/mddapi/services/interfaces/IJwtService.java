package com.openclassrooms.mddapi.services.interfaces;


import io.jsonwebtoken.Claims;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

public interface IJwtService {

    String generateToken(String username);

    String extractIdentifier(String token);

    <T> T extractClaim(String token, Function<Claims, T> claimResolver);

    Claims extractAllClaims(String token);

    boolean hasTokenNotExpired(String token);

    boolean isTokenExpired(String token);

    Date extractExpiration(String token);

    SecretKey getKey();
}
