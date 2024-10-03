package com.openclassrooms.mddapi.controller;

import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.mddapi.dto.UserLoginRequest;
import com.openclassrooms.mddapi.model.Themes;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController  // Indique que cette classe est un contrôleur REST, ce qui permet de définir des routes HTTP
@CrossOrigin  // Autorise les requêtes provenant d'autres origines (CORS - Cross-Origin Resource Sharing)
@RequestMapping("/api")  // Définit un préfixe pour toutes les routes de ce contrôleur
@Api(tags = "Users", description = "Operations related to users")  // Utilisé pour la documentation Swagger
public class UserController {

    // Injecte le service utilisateur pour accéder aux fonctionnalités métiers liées aux utilisateurs
    @Autowired
    private UserService userService;

    // Endpoint pour obtenir la liste de tous les utilisateurs
    @GetMapping("/users")
    @ApiOperation(value = "Get all users", notes = "Returns a list of all users.")  // Documente cette route pour Swagger
    public Iterable<User> getUsers() {
        // Retourne tous les utilisateurs en appelant le service utilisateur
        return userService.getUsers();
    }

    // Endpoint pour obtenir un utilisateur spécifique via son ID
    @GetMapping("/user/{id}")
    @ApiOperation(value = "Get user by ID", notes = "Returns a user by its ID.")  // Documentation Swagger
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        // Recherche l'utilisateur par ID via le service
        Optional<User> user = userService.getUserById(id);
        // Si l'utilisateur est trouvé, retourne une réponse HTTP 200 (OK) avec les données de l'utilisateur
        if (user.isPresent()) {
            return new ResponseEntity<>(user.get(), HttpStatus.OK);
        } else {
            // Sinon, retourne une réponse HTTP 404 (NOT FOUND)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Endpoint pour créer un nouvel utilisateur
    @PostMapping("auth/register")
    @ApiOperation(value = "Create a new user", notes = "Creates a new user.")  // Documentation Swagger
    public ResponseEntity<String> createUser(@RequestBody User user) {
        // Sauvegarde le nouvel utilisateur en appelant le service
        User savedUser = userService.saveUser(user);
        // Si l'utilisateur est créé avec succès, retourne une réponse HTTP 201 (CREATED)
        if (savedUser != null) {
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } else {
            // Sinon, retourne une réponse HTTP 500 (INTERNAL SERVER ERROR)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Endpoint pour authentifier un utilisateur et générer un token JWT
    @PostMapping("auth/login")
    @ApiOperation(value = "Authenticate user", notes = "Authenticate a user and return a JWT token.")  // Documentation Swagger
    public ResponseEntity<String> authenticateUser(@RequestBody UserLoginRequest loginRequest) {
        // Authentifie l'utilisateur via son email et mot de passe, et génère un token JWT
        String token = userService.authenticate(loginRequest.getEmail(), loginRequest.getPassword());
        // Si l'authentification réussit, retourne le token au format JSON
        if (token != null) {
            return ResponseEntity.ok("{\"token\":\"" + token + "\"}");
        } else {
            // Si l'authentification échoue, retourne une réponse HTTP 401 (UNAUTHORIZED)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }

    // Endpoint pour obtenir les informations de l'utilisateur courant à partir du token JWT
    @GetMapping("/auth/me")
    @ApiOperation(value = "Get current user", notes = "Returns the current user.")  // Documentation Swagger
    public ResponseEntity<User> getCurrentUser(@RequestHeader("Authorization") String authorizationHeader) {
        // Extrait le token JWT de l'en-tête "Authorization", en enlevant "Bearer "
        String token = authorizationHeader.substring(7);
        // Récupère l'email de l'utilisateur à partir du token
        String email = userService.getEmailFromToken(token);
        // Recherche l'utilisateur par email
        Optional<User> user = userService.getUserByEmail(email);
        // Si l'utilisateur est trouvé, retourne une réponse HTTP 200 (OK) avec les données de l'utilisateur
        if (user.isPresent()) {
            return new ResponseEntity<>(user.get(), HttpStatus.OK);
        } else {
            // Sinon, retourne une réponse HTTP 404 (NOT FOUND)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
        // update email and username
        @PutMapping("/auth/me")
        @ApiOperation(value = "Update email and username", notes = "Updates the email and username of the current user.")
        public ResponseEntity<User> updateUser(@RequestHeader("Authorization") String authorizationHeader, @RequestBody User updatedUser) {
            String token = authorizationHeader.substring(7);
            String email = userService.getEmailFromToken(token);
            User user = userService.getUserByEmail(email).get();
            user.setEmail(updatedUser.getEmail());
            user.setUsername(updatedUser.getUsername());
            User updatedRecord = userService.updateUser(user);
            return new ResponseEntity<>(updatedRecord, HttpStatus.OK);
        }
// Endpoint pour souscrire un utilisateur à un thème
@PostMapping("/auth/subscribe/{themeId}")  // Définit une route POST avec un paramètre "themeId"
@ApiOperation(value = "Subscribe to a theme", notes = "Subscribe to a theme.")  // Documentation Swagger pour décrire l'opération de l'API
public ResponseEntity<User> getCurrentUser(@RequestHeader("Authorization") String authorizationHeader, 
                                           @PathVariable Long themeId) {
    // Affiche dans la console le thème auquel l'utilisateur souhaite s'abonner (pour débogage)
    System.err.println("themeId: " + themeId);
    
    // Extraction du token d'authentification à partir du header "Authorization"
    // Le token commence par "Bearer ", donc on extrait la partie à partir du 7ème caractère
    String token = authorizationHeader.substring(7);
    
    // Récupération de l'utilisateur correspondant au token via le service userService
    User user = userService.getUserByToken(token);
    
    // Souscription de l'utilisateur au thème, en passant l'ID de l'utilisateur et l'ID du thème
    User updatedUser = userService.suscribeToTheme(user.getId(), themeId);
    
    // Retourne l'utilisateur mis à jour avec le statut HTTP 200 (OK)
    return new ResponseEntity<>(updatedUser, HttpStatus.OK);
}
        // unsubscribe from a theme
        @DeleteMapping("/auth/unsubscribe/{themeId}")
        @ApiOperation(value = "Unsubscribe from a theme", notes = "Unsubscribe from a theme.")
        public ResponseEntity<User> unsubscribeFromTheme(@RequestHeader("Authorization") String authorizationHeader , @PathVariable Long themeId) {
            String token = authorizationHeader.substring(7);
            User user = userService.getUserByToken(token);
            System.err.println("user: " + user.getId() + " theme: " + themeId);
            userService.unsubscribeFromTheme(user.getId(), themeId);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        //list of user themes
        @GetMapping("/auth/themes")
        @ApiOperation(value = "Get user themes", notes = "Returns the themes of the current user.")
        public ResponseEntity<Set<Themes>> getUserThemes(@RequestHeader("Authorization") String authorizationHeader) {
            String token = authorizationHeader.substring(7);
            User user = userService.getUserByToken(token);
            return new ResponseEntity<>(user.getThemes(), HttpStatus.OK);
        }
}
