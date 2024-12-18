package com.openclassrooms.mddapi.configuration;

import com.openclassrooms.mddapi.repositories.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration // Indique à Spring que cette classe contient des configurations pour l'application
public class ApplicationConfiguration {
    private final UserRepository userRepository;

    // Le constructeur permet d'injecter une dépendance à UserRepository
    public ApplicationConfiguration(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Ce bean permet de récupérer les détails d'un utilisateur à partir de la base de données via son email
    @Bean
    UserDetailsService userDetailsService() {
        return username -> userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non référencé."));
    }

    // Bean qui configure l'encodeur de mot de passe en utilisant BCrypt
    @Bean
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Bean qui configure le gestionnaire d'authentification de Spring Security
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    // Bean qui configure la stratégie d'authentification à utiliser
    @Bean
    AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService()); // Définit la source des détails utilisateur
        authProvider.setPasswordEncoder(passwordEncoder()); // Définit l'encodeur de mot de passe
        return authProvider;
    }
}
