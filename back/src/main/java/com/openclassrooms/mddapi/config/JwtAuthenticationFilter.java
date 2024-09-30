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

    // Injection de la clé secrète pour signer et valider les jetons JWT depuis les propriétés de configuration
    @Value("${jwt}")
    private String jwtSecret;

    // Méthode principale du filtre qui s'exécute à chaque requête HTTP
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            // Extraction du JWT depuis la requête HTTP
            String jwtToken = extractJwtFromRequest(request);
            
            // Validation du JWT si celui-ci est présent
            if (jwtToken != null && validateJwtToken(jwtToken)) {
                // Si le JWT est valide, on obtient l'objet d'authentification à partir du JWT
                Authentication authentication = getAuthentication(jwtToken);
                
                // Enregistrement de l'utilisateur authentifié dans le contexte de sécurité de Spring Security
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            // Gestion des erreurs liées au JWT (invalidité, expiration, etc.)
            System.err.println("Error processing JWT -> Message: " + e.getMessage());
        }
        
        // Passe la requête au filtre suivant dans la chaîne de filtres
        filterChain.doFilter(request, response);
    }

    // Méthode pour extraire le jeton JWT depuis les en-têtes de la requête HTTP
    private String extractJwtFromRequest(HttpServletRequest request) {
        // Récupère le header "Authorization" de la requête
        String bearerToken = request.getHeader("Authorization");

        // Vérifie si le token est présent et commence par "Bearer "
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            // Retourne le token sans le préfixe "Bearer "
            return bearerToken.substring(7);
        }

        // Retourne null si aucun token n'est trouvé ou s'il n'a pas le bon format
        return null;
    }

    // Méthode pour valider le jeton JWT
    private boolean validateJwtToken(String jwtToken) {
        try {
            // Valide le jeton en utilisant la clé secrète pour vérifier la signature et extraire le corps du token
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(jwtToken).getBody();
            return true;  // Si le token est valide, retourne true
        } catch (SignatureException | MalformedJwtException | ExpiredJwtException | UnsupportedJwtException | IllegalArgumentException e) {
            // Si une erreur survient lors de la validation du token, on capture l'exception
            System.err.println("JWT validation error -> Message: " + e.getMessage());
            return false;  // Si le token est invalide, retourne false
        }
    }

    // Méthode pour extraire les informations d'authentification à partir du jeton JWT
    private Authentication getAuthentication(String jwtToken) {
        // Récupère les claims (données contenues dans le JWT) en utilisant la clé secrète
        Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(jwtToken).getBody();
        
        // Extrait l'email de l'utilisateur à partir des claims du JWT (le "subject" du JWT)
        String email = claims.getSubject();
        
        // Si l'email est présent, on crée et retourne un objet d'authentification
        if (email != null) {
            return new UsernamePasswordAuthenticationToken(email, null, null);
        }
        
        // Si aucune information d'authentification n'est trouvée, retourne null
        return null;
    }
}
