package com.openclassrooms.mddapi.service;

import java.io.Serializable;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.openclassrooms.mddapi.config.JwtConfig;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.repository.UserRepository;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service  // Indique que cette classe est un service Spring
public class UserService {
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;  // Injection de dépendance pour le codage des mots de passe

    @Autowired
    private JwtConfig jwtConfig;  // Injection de dépendance pour la configuration JWT

    @Autowired
    private UserRepository userRepository;  // Injection de dépendance pour le repository des utilisateurs

    // Classe d'exception personnalisée pour gérer les cas où un utilisateur n'est pas trouvé
    public class NotFoundException extends RuntimeException implements Serializable {
        private static final long serialVersionUID = 1L;  // ID de version pour la sérialisation

        public NotFoundException(String message) {
            super(message);  // Appelle le constructeur de la classe parente avec le message
        }
    }

    // Service pour obtenir tous les utilisateurs
    public Iterable<User> getUsers() {
        return userRepository.findAll();  // Récupère tous les utilisateurs via le repository
    }

    // Service pour obtenir un utilisateur par son ID
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);  // Récupère un utilisateur par son ID
    }

    // Service pour sauvegarder un utilisateur
    public User saveUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));  // Encode le mot de passe
        user.setCreatedAt(java.time.LocalDateTime.now());  // Définit la date de création
        User savedUser = userRepository.save(user);  // Sauvegarde l'utilisateur dans la base de données
        return savedUser;  // Retourne l'utilisateur sauvegardé
    }

    // Service pour supprimer un utilisateur
    public void deleteUser(Long userId) {
        User existingUser = userRepository.findById(userId).orElse(null);  // Cherche l'utilisateur par ID
        if (existingUser != null) {
            userRepository.delete(existingUser);  // Supprime l'utilisateur s'il existe
        } else {
            throw new NotFoundException("Enregistrement introuvable");  // Lève une exception si l'utilisateur n'est pas trouvé
        }
    }

    // Service pour mettre à jour le mot de passe d'un utilisateur
    public User updatePassword(User updatePassword) {
        User existingUser = userRepository.findById(updatePassword.getId()).orElse(null);  // Cherche l'utilisateur existant
        if (existingUser != null) {
            existingUser.setPassword(bCryptPasswordEncoder.encode(updatePassword.getPassword()));  // Encode le nouveau mot de passe
            existingUser.setUpdatedAt(java.time.LocalDateTime.now());  // Définit la date de mise à jour
        }
        User updatedRecord = userRepository.save(existingUser);  // Sauvegarde les modifications
        return updatedRecord;  // Retourne l'utilisateur mis à jour
    }

    // Service pour mettre à jour les informations d'un utilisateur
    public User updateUser(User updatedUser) {
        User existingUser = userRepository.findById(updatedUser.getId()).orElse(null);  // Cherche l'utilisateur existant
        if (existingUser != null) {
            existingUser.setUsername(updatedUser.getUsername());  // Met à jour le nom d'utilisateur
            existingUser.setEmail(updatedUser.getEmail());  // Met à jour l'email
            User updatedRecord = userRepository.save(existingUser);  // Sauvegarde les modifications
            return updatedRecord;  // Retourne l'utilisateur mis à jour
        } else {
            throw new NotFoundException("Enregistrement introuvable");  // Lève une exception si l'utilisateur n'est pas trouvé
        }
    }

    // Service pour authentifier un utilisateur
    public String authenticate(String email, String password) {
        Optional<User> optionalUser = userRepository.findByEmail(email);  // Cherche l'utilisateur par email
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();  // Récupère l'utilisateur trouvé
            if (bCryptPasswordEncoder.matches(password, user.getPassword())) {  // Vérifie si le mot de passe correspond
                long expirationTimeInMillis = jwtConfig.getJwtExpirationMs();  // Récupère la durée d'expiration du JWT
                String token = Jwts.builder()  // Crée un nouveau JWT
                        .setSubject(email)  // Définit le sujet du JWT (email de l'utilisateur)
                        .setExpiration(new Date(System.currentTimeMillis() + expirationTimeInMillis))  // Définit la date d'expiration
                        .signWith(SignatureAlgorithm.HS256, jwtConfig.getJwtSecret())  // Signe le JWT avec le secret
                        .compact();  // Compacte le JWT
                return token;  // Retourne le token JWT
            } else {
                throw new NotFoundException("Mot de passe incorrect");  // Lève une exception si le mot de passe est incorrect
            }
        } else {
            throw new NotFoundException("Utilisateur introuvable");  // Lève une exception si l'utilisateur n'est pas trouvé
        }
    }

    // Service pour obtenir l'email à partir d'un token
    public String getEmailFromToken(String token) {
        String email = Jwts.parser()  // Parse le token JWT
                .setSigningKey(jwtConfig.getJwtSecret())  // Définit la clé de signature
                .parseClaimsJws(token)  // Parse le JWT
                .getBody()  // Récupère le corps du JWT
                .getSubject();  // Récupère le sujet (email)
        return email;  // Retourne l'email
    }

    // Service pour obtenir un utilisateur par email
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);  // Récupère l'utilisateur par email
    }

    // Service pour obtenir un utilisateur par token
    public User getUserByToken(String token) {
        String email = getEmailFromToken(token);  // Récupère l'email à partir du token
        return getUserByEmail(email)  // Récupère l'utilisateur par email
                .orElseThrow(() -> new NotFoundException("Utilisateur introuvable"));  // Lève une exception si l'utilisateur n'est pas trouvé
    }
}
