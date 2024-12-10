package com.openclassrooms.mddapi.service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
public class JwtService {
    private JwtEncoder jwtEncoder;
    private JwtDecoder jwtDecoder;
    /**
     * Constructs a new JwtService with the given JwtEncoder.
     *
     * @param jwtEncoder the JwtEncoder used for encoding JWT tokens
     */
    public JwtService(@Value("${jwt.secret.key}") String jwtKey, JwtEncoder jwtEncoder) {
        this.jwtEncoder = jwtEncoder;
        SecretKey secretKey = new SecretKeySpec(jwtKey.getBytes(), "HmacSHA256");
        this.jwtDecoder = NimbusJwtDecoder.withSecretKey(secretKey).build();
    }
    /**
     * Generates a JWT token for the given authentication.
     *
     * @param authentication the authentication object containing the principal
     * @return a JWT token as a String
     */
    public String generateToken(Authentication authentication) {
        Instant now = Instant.now();
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("http://localhost:4200")
                .issuedAt(now)
                .expiresAt(now.plus(1, ChronoUnit.DAYS))
                .subject(authentication.getPrincipal().toString())
                .build();
        JwtEncoderParameters jwtEncoderParameters = JwtEncoderParameters.from(JwsHeader.with(MacAlgorithm.HS256).build(), claims);
        return this.jwtEncoder.encode(jwtEncoderParameters).getTokenValue();
    }
    /**
     /**
     * Validates a JWT bearer token, ensuring it is well-formed and not expired.
     * If the token is valid, it returns the subject ("sub") claim from the token.
     * If the token is invalid or expired, it throws an appropriate exception.
     *
     * @param bearerToken The bearer token to validate, expected to start with "Bearer ".
     * @return The subject claim ("sub") from the token if valid.
     * @throws ResponseStatusException If the token is invalid or expired.
     */
    public String validateToken(String bearerToken) {
        try {
            if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
                String token = bearerToken.substring(7);
                Jwt jwt = jwtDecoder.decode(token);
                Instant expiration = jwt.getExpiresAt();

                if (expiration != null && expiration.isBefore(Instant.now())) {
                    throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Token expired");
                }
                return (String)jwt.getClaims().get("sub");
            } else {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Token invalide");
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Token invalide");
        }
    }
}