package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.dto.MessageDto;
import com.openclassrooms.mddapi.dto.UserLoginDto;
import com.openclassrooms.mddapi.dto.UserProfilDto;
import com.openclassrooms.mddapi.dto.UserRegisterDto;
import com.openclassrooms.mddapi.service.JwtService;
import com.openclassrooms.mddapi.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private JwtService jwtService;
    /**
     * Handles user login requests by validating credentials and returning a JWT token if successful.
     *
     * @param userLogin The DTO containing the user's login credentials (email/username and password).
     * @return A ResponseEntity containing the token if login is successful, or an error message if login fails.
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginDto userLogin) {
        return userService.checkLogin(userLogin);
    }
    /**
     * Handles user registration requests and creates a new user if the registration is successful.
     *
     * @param userRegister The DTO containing the user's registration information.
     * @return A ResponseEntity containing the registration token if successful, or a conflict status if registration fails.
     */
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserRegisterDto userRegister) {
        try {
            return userService.registerUser(userRegister);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Erreur lors de l'inscription: " + e.getMessage());
        }
    }
    /**
     * Retrieves the profile of the authenticated user, including their subscribed themes.
     *
     * @param token The JWT token from the request header used to authenticate the user.
     * @return A ResponseEntity containing the user's profile and subscribed themes.
     */
    @GetMapping("/profil")
    public ResponseEntity<?> profil(@RequestHeader("Authorization") String token){
        String email = jwtService.validateToken(token);
        return userService.getUserProfileWithThemes(email);
    }
    /**
     * Updates the authenticated user's profile information (e.g., username, email).
     *
     * @param token The JWT token from the request header used to authenticate the user.
     * @param userUpdate The DTO containing the updated profile information.
     * @return A MessageDto confirming the successful update of the user's profile.
     */
    @PutMapping("/profil")
    public MessageDto updateProfile(@RequestHeader("Authorization") String token, @Valid @RequestBody UserProfilDto userUpdate) {
        String email = jwtService.validateToken(token);
        return userService.updateUserProfile(userUpdate);
    }
    /**
     * Retrieves the themes the authenticated user is subscribed to.
     *
     * @param token The JWT token from the request header used to authenticate the user.
     * @return A ResponseEntity containing a list of the user's subscribed themes.
     */
    @GetMapping("/themes")
    public ResponseEntity<?> getUserThemes(@RequestHeader("Authorization") String token) {
        String email = jwtService.validateToken(token);
        return userService.getUserThemes(email);
    }
}
