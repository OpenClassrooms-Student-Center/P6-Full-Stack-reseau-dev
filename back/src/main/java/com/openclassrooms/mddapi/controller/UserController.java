package com.openclassrooms.mddapi.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.mddapi.dto.UserLoginRequest;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController  // Indique que cette classe est un contrôleur REST
@CrossOrigin  // Autorise les requêtes cross-origin (CORS)
@RequestMapping("/api")  // Définit un préfixe pour les routes de ce contrôleur
@Api(tags = "Users", description = "Operations related to users")  // Documentation Swagger pour le contrôleur
public class UserController {

    // Injection de dépendance pour accéder aux services utilisateur
    @Autowired
    private UserService userService;

    // Endpoint pour obtenir la liste de tous les utilisateurs
    @GetMapping("/users")
    @ApiOperation(value = "Get all users", notes = "Returns a list of all users.")  // Documentation Swagger
    public Iterable<User> getUsers() {
        // Retourne tous les utilisateurs via le service
        return userService.getUsers();
    }

    // Endpoint pour obtenir un utilisateur spécifique via son ID
    @GetMapping("/user/{id}")
    @ApiOperation(value = "Get user by ID", notes = "Returns a user by its ID.")  // Documentation Swagger
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        // Recherche l'utilisateur par ID via le service
        Optional<User> user = userService.getUserById(id);
        // Si l'utilisateur est trouvé, retourne un statut 200 et l'utilisateur
        if (user.isPresent()) {
            return new ResponseEntity<>(user.get(), HttpStatus.OK);
        } else {
            // Sinon, retourne un statut 404 (utilisateur non trouvé)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Endpoint pour créer un nouvel utilisateur
    @PostMapping("auth/register")
    @ApiOperation(value = "Create a new user", notes = "Creates a new user.")  // Documentation Swagger
    public ResponseEntity<String> createUser(@RequestBody User user) {
        // Sauvegarde le nouvel utilisateur via le service
        User savedUser = userService.saveUser(user);
        // Si l'utilisateur est créé avec succès, retourne un statut 201 (Created)
        if (savedUser != null) {
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } else {
            // Sinon, retourne un statut 500 (Internal Server Error)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
// Endpoint pour authentifier un utilisateur et générer un token JWT
@PostMapping("auth/login")
@ApiOperation(value = "Authenticate user", notes = "Authenticate a user and return a JWT token.")  // Documentation Swagger
public ResponseEntity<String> authenticateUser(@RequestBody UserLoginRequest loginRequest) {
    // Authentifie l'utilisateur via son email et mot de passe, et génère un token
    String token = userService.authenticate(loginRequest.getEmail(), loginRequest.getPassword());
    // Si l'authentification réussit et un token est généré
    if (token != null) {
        // Retourne le token en tant que chaîne JSON
        return ResponseEntity.ok("{\"token\":\"" + token + "\"}");
    } else {
        // Si les identifiants sont incorrects, retourne un statut 401 (Unauthorized)
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
    }
}


    // Endpoint pour obtenir les informations de l'utilisateur actuel via le token JWT
    @GetMapping("/auth/me")
    @ApiOperation(value = "Get current user", notes = "Returns the current user.")  // Documentation Swagger
    public ResponseEntity<User> getCurrentUser(@RequestHeader("Authorization") String authorizationHeader) {
        // Extrait le token JWT de l'en-tête Authorization (en enlevant "Bearer ")
        String token = authorizationHeader.substring(7);
        // Récupère l'email de l'utilisateur à partir du token
        String email = userService.getEmailFromToken(token);
        // Recherche l'utilisateur par email
        Optional<User> user = userService.getUserByEmail(email);
        // Si l'utilisateur est trouvé, retourne un statut 200 et les informations de l'utilisateur
        if (user.isPresent()) {
            return new ResponseEntity<>(user.get(), HttpStatus.OK);
        } else {
            // Sinon, retourne un statut 404 (utilisateur non trouvé)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
