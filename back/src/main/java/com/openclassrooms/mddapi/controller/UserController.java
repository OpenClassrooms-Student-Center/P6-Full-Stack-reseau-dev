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
@CrossOrigin  // Autorise les requêtes cross-origin
@RequestMapping("/api")  // Préfixe toutes les URL de cette classe avec /api
@Api(tags = "Users", description = "Operations related to users")  // Documentation Swagger pour le contrôleur
public class UserController {

    @Autowired
    private UserService userService;  // Injection du service utilisateur

    // Endpoint pour obtenir tous les utilisateurs
    @GetMapping("/users")
    @ApiOperation(value = "Get all users", notes = "Returns a list of all users.")  // Documentation Swagger
    public Iterable<User> getUsers() {
        return userService.getUsers();  // Retourne la liste des utilisateurs
    }

    // Endpoint pour obtenir un utilisateur par son ID
    @GetMapping("/user/{id}")
    @ApiOperation(value = "Get user by ID", notes = "Returns a user by its ID.")  // Documentation Swagger
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        Optional<User> user = userService.getUserById(id);  // Récupération de l'utilisateur par ID
        if (user.isPresent()) {
            return new ResponseEntity<>(user.get(), HttpStatus.OK);  // Retourne l'utilisateur avec un statut 200
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);  // Retourne un statut 404 si l'utilisateur n'existe pas
        }
    }

    // Endpoint pour créer un nouvel utilisateur
    @PostMapping("auth/register")
    @ApiOperation(value = "Create a new user", notes = "Creates a new user.")  // Documentation Swagger
    public ResponseEntity<String> createUser(@RequestBody User user) {
        User savedUser = userService.saveUser(user);  // Sauvegarde de l'utilisateur
        if (savedUser != null) {
            return ResponseEntity.status(HttpStatus.CREATED).build();  // Retourne un statut 201 si l'utilisateur est créé
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();  // Retourne un statut 500 en cas d'erreur
        }
    }

    // Endpoint pour authentifier un utilisateur
    @PostMapping("auth/login")
    @ApiOperation(value = "Authenticate user", notes = "Authenticate a user and return a JWT token.")  // Documentation Swagger
    public ResponseEntity<String> authenticateUser(@RequestBody UserLoginRequest loginRequest) {
        String token = userService.authenticate(loginRequest.getEmail(), loginRequest.getPassword());  // Authentification de l'utilisateur
        if (token != null) {
            return ResponseEntity.ok(token);  // Retourne le token JWT si l'authentification est réussie
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");  // Retourne un statut 401 si les identifiants sont invalides
        }
    }

    // Endpoint pour obtenir les informations de l'utilisateur actuel
    @GetMapping("/auth/me")
    @ApiOperation(value = "Get current user", notes = "Returns the current user.")  // Documentation Swagger
    public ResponseEntity<User> getCurrentUser(@RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.substring(7);  // Extraction du token JWT de l'en-tête Authorization
        String email = userService.getEmailFromToken(token);  // Récupération de l'email à partir du token
        Optional<User> user = userService.getUserByEmail(email);  // Récupération de l'utilisateur par email
        if (user.isPresent()) {
            return new ResponseEntity<>(user.get(), HttpStatus.OK);  // Retourne l'utilisateur avec un statut 200
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);  // Retourne un statut 404 si l'utilisateur n'est pas trouvé
        }
    }
}
