package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.configuration.ThemeMapper;
import com.openclassrooms.mddapi.dto.*;
import com.openclassrooms.mddapi.model.ThemeModel;
import com.openclassrooms.mddapi.model.UserModel;
import com.openclassrooms.mddapi.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private JwtService jwtService;
    /**
     * Checks if the provided password matches the user's stored password.
     *
     * @param userLoginDto The DTO containing the login information, including the password to check.
     * @param user The user whose password is being verified.
     * @return true if the passwords match, false otherwise.
     */
    public boolean checkPassword(UserLoginDto userLoginDto, UserModel user) {
        return passwordEncoder.matches(userLoginDto.getPassword(), user.getPassword());
    }
    /**
     * Validates user login credentials, checks email and password, and generates a JWT token if successful.
     *
     * @param userLoginDto The DTO containing the login details (email/username and password).
     * @return A ResponseEntity containing a TokenDto if login is successful, or an error message if not.
     */
    public ResponseEntity<?> checkLogin(UserLoginDto userLoginDto) {
        UserModel foundUser = userRepository.findByEmail(userLoginDto.getEmail());
        if(foundUser == null) {
            foundUser = userRepository.findByUsername(userLoginDto.getEmail());
        }
        if(foundUser != null) {
            boolean bcheckPassword = checkPassword(userLoginDto, foundUser);
            if(bcheckPassword) {
                Authentication authentication = new UsernamePasswordAuthenticationToken(foundUser.getEmail(), null, new ArrayList<>());
                String token = jwtService.generateToken(authentication);
                TokenDto tokenDto = new TokenDto(token, foundUser.getId(), foundUser.getUsername(), foundUser.getEmail());
                return ResponseEntity.ok(tokenDto);
            }else{
                // Mot de pass incorrect
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Email ou mot de passe incorrect");
            }
        }else {
            // Email incorrect
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Email ou mot de passe incorrect");
        }
    }
    /**
     * Registers a new user in the system.
     *
     * @param userRegister The DTO containing the user registration details.
     * @return A ResponseEntity containing a TokenDto for the newly registered user, or a conflict status if the email already exists.
     */
    public ResponseEntity<?> registerUser(UserRegisterDto userRegister) {
        UserModel foundUser = userRepository.findByEmail(userRegister.getEmail());
        if(foundUser == null) {
            UserModel user = modelMapper.map(userRegister, UserModel.class);
            user.setPassword(passwordEncoder.encode(userRegister.getPassword()));
            UserModel newUser = userRepository.save(user);
            Authentication authentication = new UsernamePasswordAuthenticationToken(newUser.getEmail(), null, new ArrayList<>());
            String token = jwtService.generateToken(authentication);
            TokenDto tokenDto = new TokenDto(token, newUser.getId(), newUser.getUsername(), newUser.getEmail());
            return ResponseEntity.ok(tokenDto);
        }else{
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Un utilisateur avec cet email existe déjà");
        }
    }
    /**
     * Updates the user's profile details such as username and email.
     *
     * @param userUpdate The DTO containing the updated user profile information.
     * @return A MessageDto confirming the successful profile update.
     */
    public MessageDto updateUserProfile(UserProfilDto userUpdate) {
        UserModel user = userRepository.findByEmail(userUpdate.getEmail());

        user.setUsername(userUpdate.getUsername());
        user.setEmail(userUpdate.getEmail());
        userRepository.save(user);

        return new MessageDto("User profile updated successfully");
    }
    /**
     * Retrieves the user's profile along with their subscribed themes.
     *
     * @param userEmail The email of the user whose profile is being requested.
     * @return A ResponseEntity containing the user's profile and theme subscriptions, or a 404 status if the user is not found.
     */
    public ResponseEntity<?> getUserProfileWithThemes(String userEmail) {
        UserModel user = userRepository.findByEmail(userEmail);

        if (user != null) {

            List<ThemeDto> themeDtos = user.getThemes().stream()
                    .map(theme ->{
                        ThemeDto dto = ThemeMapper.toDTO(theme);
                        dto.setSubscribed(true);
                        return dto;
                    })
                    .collect(Collectors.toList());
            UserProfilDto userProfilDto = new UserProfilDto(
                    user.getUsername(),
                    user.getEmail(),
                    themeDtos
            );

            return ResponseEntity.ok().body(userProfilDto);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }
    /**
     * Retrieves the list of themes the user is subscribed to.
     *
     * @param email The email of the user whose subscribed themes are requested.
     * @return A ResponseEntity containing a list of subscribed theme DTOs.
     */
    public ResponseEntity<?> getUserThemes(String email) {
        UserModel user= userRepository.findByEmail(email);
            List<ThemeModel> themes = new ArrayList<>(user.getThemes());

            List<ThemeDto> themeDtos = themes.stream()
                    .map(theme ->{
                        ThemeDto dto = ThemeMapper.toDTO(theme);
                        dto.setSubscribed(true);
                        return dto;
                    })
                    .collect(Collectors.toList());
            return ResponseEntity.ok(themeDtos);
    }
}
