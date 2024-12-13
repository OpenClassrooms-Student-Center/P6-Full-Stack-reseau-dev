package com.openclassrooms.mddapi.service;

import java.io.Serializable;
import java.util.Date;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.openclassrooms.mddapi.config.JwtConfig;
import com.openclassrooms.mddapi.model.Themes;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.repository.ThemesRepository;
import com.openclassrooms.mddapi.repository.UserRepository;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service  // Indique que cette classe est un service Spring
public class UserService {

    // Injecte un encodeur BCrypt pour gérer le hash des mots de passe
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    // Injecte la configuration JWT, probablement utilisée pour la gestion des tokens
    @Autowired
    private JwtConfig jwtConfig;

    // Injecte le repository des thèmes pour manipuler les entités "Themes"
    @Autowired
    private ThemesRepository themeRepository;

    // Injecte le repository des utilisateurs pour manipuler les entités "User"
    @Autowired
    private UserRepository userRepository;

    // Exception personnalisée pour gérer les cas où un utilisateur n'est pas trouvé
    public class NotFoundException extends RuntimeException implements Serializable {

        private static final long serialVersionUID = 1L;  // ID de version pour la sérialisation

        public NotFoundException(String message) {
            super(message);  // Appelle le constructeur parent avec un message d'erreur
        }
    }

    // Méthode pour obtenir tous les utilisateurs
    public Iterable<User> getUsers() {
        return userRepository.findAll();  // Retourne la liste de tous les utilisateurs
    }

    // Méthode pour obtenir un utilisateur par son ID
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);  // Retourne l'utilisateur correspondant à l'ID
    }

    // Sauvegarde un nouvel utilisateur en encodant son mot de passe
    public User saveUser(User user) {
        // Encode le mot de passe avant de sauvegarder
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setCreatedAt(java.time.LocalDateTime.now());  // Définit la date de création à la date actuelle
        return userRepository.save(user);
    }

    // Supprime un utilisateur en fonction de son ID
    public void deleteUser(Long userId) {
        // Cherche l'utilisateur par son ID
        User existingUser = userRepository.findById(userId).orElse(null);
        if (existingUser != null) {
            userRepository.delete(existingUser);  // Supprime l'utilisateur si trouvé
        } else {
            // Lance une exception si l'utilisateur n'est pas trouvé
            throw new NotFoundException("Enregistrement introuvable");
        }
    }

    // Met à jour le mot de passe d'un utilisateur
    public User updatePassword(User updatePassword) {
        // Cherche l'utilisateur par son ID
        User existingUser = userRepository.findById(updatePassword.getId()).orElse(null);
        if (existingUser != null) {
            // Encode et met à jour le mot de passe
            existingUser.setPassword(bCryptPasswordEncoder.encode(updatePassword.getPassword()));
            existingUser.setUpdatedAt(java.time.LocalDateTime.now());  // Met à jour la date de modification
        }
        // Sauvegarde les modifications
        User updatedRecord = userRepository.save(existingUser);
        return updatedRecord;  // Retourne l'utilisateur mis à jour
    }

    // Met à jour les informations d'un utilisateur
    public User updateUser(User updatedUser) {
        // Cherche l'utilisateur par son ID
        User existingUser = userRepository.findById(updatedUser.getId()).orElse(null);
        if (existingUser != null) {
            // Met à jour les champs de l'utilisateur
            existingUser.setUsername(updatedUser.getUsername());
            existingUser.setEmail(updatedUser.getEmail());
            return userRepository.save(existingUser);
        } else {
            // Lance une exception si l'utilisateur n'est pas trouvé
            throw new NotFoundException("Enregistrement introuvable");
        }
    }

    // Authentifie un utilisateur et retourne un token JWT
    public String authenticate(String email, String password) {
        // Cherche l'utilisateur par son email
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            // Vérifie si le mot de passe correspond
            if (bCryptPasswordEncoder.matches(password, user.getPassword())) {
                long expirationTimeInMillis = jwtConfig.getJwtExpirationMs();  // Récupère la durée d'expiration du token
                // Génère un token JWT
                String token = Jwts.builder()
                        .setSubject(email)
                        .setExpiration(new Date(System.currentTimeMillis() + expirationTimeInMillis))
                        .signWith(SignatureAlgorithm.HS256, jwtConfig.getJwtSecret())
                        .compact();
                return token;  // Retourne le token généré
            } else {
                throw new NotFoundException("Mot de passe incorrect");  // Lève une exception si le mot de passe est incorrect
            }
        } else {
            throw new NotFoundException("Utilisateur introuvable");  // Lève une exception si l'utilisateur n'est pas trouvé
        }
    }

    // Méthode pour extraire l'email depuis un token JWT
    public String getEmailFromToken(String token) {
        // Décode le token et récupère le sujet (l'email)
        String email = Jwts.parser()
                .setSigningKey(jwtConfig.getJwtSecret())
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
        return email;  // Retourne l'email extrait du token
    }

    // Récupère un utilisateur par email
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);  // Retourne l'utilisateur correspondant à l'email
    }

    // Récupère un utilisateur en fonction de son token JWT
    public User getUserByToken(String token) {
        // Extrait l'email du token et cherche l'utilisateur correspondant
        String email = getEmailFromToken(token);
        User user = getUserByEmail(email).orElseThrow(() -> new NotFoundException("Utilisateur introuvable"));
        return user;
    }

    // Souscrit un utilisateur à un thème
    public User suscribeToTheme(Long userId, Long themeId) {
        // Cherche l'utilisateur et le thème par leurs ID respectifs
        User existingUser = userRepository.findById(userId).orElse(null);
        Themes existingTheme = themeRepository.findById(themeId).orElse(null);
        System.err.println("existingUser : " + existingUser);
        System.err.println("existingTheme : " + existingTheme);
        // Ajoute le thème à la liste des thèmes de l'utilisateur
        if (existingUser != null && existingTheme != null) {
            existingUser.getThemes().add(existingTheme);
        }
        // Sauvegarde l'utilisateur avec le nouveau thème ajouté
        System.err.println("existingUser 2 : " + existingUser);
        return userRepository.save(existingUser);
    }

    // Désabonner l'utilisateur d'un thème
    public User unsubscribeFromTheme(Long userId, Long themeId) {
        User existingUser = userRepository.findById(userId).orElse(null);
        Themes existingTheme = themeRepository.findById(themeId).orElse(null);
        if (existingUser != null && existingTheme != null) {
            existingUser.getThemes().remove(existingTheme);
        }
        return userRepository.save(existingUser);
    }

    // Liste de tous les thèmes de l'utilisateur
    public Set<Themes> getThemesByUser(Long userId) {
        User existingUser = userRepository.findById(userId).orElse(null);
        if (existingUser != null) {
            return existingUser.getThemes();  // Retourne les thèmes de l'utilisateur
        } else {
            throw new NotFoundException("Utilisateur introuvable");
        }
    }
}
