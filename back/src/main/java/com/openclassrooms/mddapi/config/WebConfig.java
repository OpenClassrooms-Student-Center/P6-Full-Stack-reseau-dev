package com.openclassrooms.mddapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc  // Active la prise en charge de MVC dans l'application
public class WebConfig implements WebMvcConfigurer {

    // Méthode pour ajouter des mappings CORS (Cross-Origin Resource Sharing)
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Autoriser toutes les URL
                .allowedOrigins("*") // Autoriser toutes les origines (domains) à faire des requêtes
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD", "PATCH", "DELETE", "PUT") // Méthodes HTTP autorisées
                .allowedHeaders("*");  // Autoriser tous les en-têtes
    }

    // Bean pour configurer un filtre CORS
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource(); // Source de configuration basée sur l'URL
        CorsConfiguration config = new CorsConfiguration(); // Création d'une configuration CORS
        config.applyPermitDefaultValues(); // Appliquer les valeurs par défaut pour CORS
        source.registerCorsConfiguration("/**", config); // Enregistrer la configuration CORS pour toutes les URL
        return new CorsFilter(source); // Retourner un nouveau filtre CORS avec la configuration
    }

}
