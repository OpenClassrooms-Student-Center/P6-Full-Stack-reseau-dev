package com.openclassrooms.mddapi.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    // Injection de la clé secrète à partir des propriétés de configuration 
    @Value("${jwt}")
    private String jwtSecret;

    // Méthode principale du filtre qui s'exécute pour chaque requête HTTP
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            // Extraction du jeton JWT depuis la requête HTTP
            String jwtToken = extractJwtFromRequest(request);

            // Validation du JWT si celui-ci est présent
            if (jwtToken != null && validateJwtToken(jwtToken)) {
                // Si le JWT est valide, on obtient les informations d'authentification
                Authentication authentication = getAuthentication(jwtToken);

                // Ajout de l'authentification au contexte de sécurité de Spring
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            // Gestion des erreurs liées au JWT (expiration, invalidité, etc.)
            System.err.println("Erreur lors du traitement du JWT -> Message: " + e.getMessage());
        }

        // Passage de la requête au filtre suivant dans la chaîne de filtres
        filterChain.doFilter(request, response);
    }

    // Méthode pour extraire le jeton JWT depuis l'en-tête HTTP "Authorization"
    private String extractJwtFromRequest(HttpServletRequest request) {
        // Récupère l'en-tête "Authorization"
        String bearerToken = request.getHeader("Authorization");

        // Vérifie si le token commence par "Bearer " et renvoie le token sans le préfixe
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);  // Retourne le JWT sans "Bearer "
        }

        // Si aucun token valide n'est trouvé, retourne null
        return null;
    }

    // Méthode pour valider le JWT à l'aide de la clé secrète
    private boolean validateJwtToken(String jwtToken) {
        try {
            // Validation du token en utilisant la clé secrète (vérifie la signature)
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(jwtToken).getBody();
            return true;  // Si le token est valide, retourne true
        } catch (SignatureException | MalformedJwtException | ExpiredJwtException | UnsupportedJwtException | IllegalArgumentException e) {
            // Gestion des exceptions liées à un JWT invalide
            System.err.println("Erreur de validation du JWT -> Message: " + e.getMessage());
            return false;  // Si le token est invalide, retourne false
        }
    }

    // Méthode pour extraire les informations d'authentification à partir du JWT
    private Authentication getAuthentication(String jwtToken) {
        // Extraction des claims (données du JWT) avec la clé secrète
        Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(jwtToken).getBody();

        // Récupère le "subject" (souvent l'email ou l'identifiant de l'utilisateur) du JWT
        String email = claims.getSubject();

        // Si l'email est présent, crée et retourne un objet d'authentification
        if (email != null) {
            // L'objet UsernamePasswordAuthenticationToken contient les informations d'authentification
            return new UsernamePasswordAuthenticationToken(email, null, null);
        }

        // Retourne null si aucune authentification n'est trouvée
        return null;
    }
}
