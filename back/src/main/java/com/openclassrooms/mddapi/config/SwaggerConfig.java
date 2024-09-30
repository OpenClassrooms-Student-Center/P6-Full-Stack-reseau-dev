package com.openclassrooms.mddapi.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiKey;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    // Bean pour configurer Swagger pour générer la documentation de l'API
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)  // Utilisation de Swagger 2 pour la documentation
                .select()
                .apis(RequestHandlerSelectors.any())  // Documenter toutes les APIs présentes dans l'application
                .paths(PathSelectors.any())  // Inclure tous les chemins des endpoints dans la documentation
                .build()
                .securitySchemes(Arrays.asList(apiKey()));  // Ajouter le schéma de sécurité JWT pour Swagger
    }

    // Méthode pour configurer le schéma de sécurité basé sur les tokens JWT dans Swagger
    private ApiKey apiKey() {
        return new ApiKey("JWT", "Authorization", "header");  // Utilisation de JWT dans l'en-tête "Authorization"
    }
}
