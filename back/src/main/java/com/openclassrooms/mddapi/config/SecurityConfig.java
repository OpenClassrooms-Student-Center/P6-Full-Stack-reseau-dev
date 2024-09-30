package com.openclassrooms.mddapi.config;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    // Injection du filtre JWT qui sera utilisé pour sécuriser les requêtes
    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    // Liste blanche des routes qui sont accessibles sans authentification
    private static final String[] AUTH_WHITELIST = {
            "/v2/api-docs",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**",
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/api/auth/login",  // Route de connexion autorisée sans authentification
            "/api/auth/register", // Route d'enregistrement autorisée sans authentification
    };

    // Configuration de la sécurité HTTP
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()  // Désactivation de la protection CSRF, car nous utilisons des tokens stateless
            .sessionManagement().sessionCreationPolicy(org.springframework.security.config.http.SessionCreationPolicy.STATELESS)  // Utilisation de sessions stateless (sans session persistante)
            .and()
            .authorizeRequests()
                .antMatchers(AUTH_WHITELIST).permitAll()  // Autorisation des routes dans la whitelist sans authentification
                .anyRequest().authenticated()  // Toute autre requête nécessite une authentification
            .and()
            .exceptionHandling()
                .authenticationEntryPoint(unauthorizedHandler());  // Gestion des erreurs d'authentification pour les requêtes non autorisées

        // Ajout du filtre JWT avant le filtre d'authentification par username/password de Spring Security
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }

    // Bean pour encoder les mots de passe avec BCrypt
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();  // Utilisation de l'encodeur BCrypt pour sécuriser les mots de passe
    }

    // Gestion des requêtes non autorisées en renvoyant un statut 401 (Unauthorized)
    @Bean
    public AuthenticationEntryPoint unauthorizedHandler() {
        return (request, response, authException) -> 
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
    }

}
