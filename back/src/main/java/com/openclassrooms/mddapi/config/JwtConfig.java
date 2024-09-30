package com.openclassrooms.mddapi.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfig {

    // Injection de la configuration de sécurité (potentiellement pour accéder à d'autres configurations)
    @Autowired
    SecurityConfig securityConfig;

    // La clé secrète utilisée pour signer et valider les jetons JWT, récupérée des propriétés de configuration
    @Value("${jwt}")
    private String jwtSecret;

    // Durée d'expiration des jetons JWT (en millisecondes), également récupérée des propriétés
    @Value("${jwtExpirationMs}")
    private int jwtExpirationMs;

    // Méthode pour récupérer la clé secrète pour signer les JWT
    public String getJwtSecret() {
        return jwtSecret;
    }

    // Méthode pour récupérer la durée d'expiration des JWT
    public int getJwtExpirationMs() {
        return jwtExpirationMs;
    }
}
