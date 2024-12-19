package com.openclassrooms.mddapi.services.impl;

import com.openclassrooms.mddapi.dto.UserDto;
import com.openclassrooms.mddapi.exception.BadRequestException;
import com.openclassrooms.mddapi.mapper.UserMapper;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.models.UserPrincipal;
import com.openclassrooms.mddapi.payload.request.LoginRequest;
import com.openclassrooms.mddapi.payload.request.SignupRequest;
import com.openclassrooms.mddapi.payload.response.JwtResponse;
import com.openclassrooms.mddapi.repositories.UserRepository;

import com.openclassrooms.mddapi.security.JwtUtils;
import com.openclassrooms.mddapi.services.interfaces.IUserDetailsService;
import com.openclassrooms.mddapi.services.interfaces.IUserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {



    private final UserRepository userRepository;
    private final JwtServiceImpl jwtServiceImpl;
    private final AuthenticationManager authManager;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
    private final UserMapper userMapper;
    private final IUserDetailsService userDetailsService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final Logger logger = LoggerFactory.getLogger(UserService.class);

    public JwtResponse authenticate(LoginRequest loginRequest) throws AuthenticationException {
        String identifier = loginRequest.getIdentifier();
        // Vérifier si l'identifiant est un email ou un username
        User user = findUserByIdentifier(identifier);

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtServiceImpl.generateToken(loginRequest.getIdentifier());
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        String connectedUserEmail = userRepository.findByUsername(userPrincipal.getUsername()).getEmail();
        return JwtResponse.builder().id(userPrincipal.getId()).email(connectedUserEmail).
                username(userPrincipal.getUsername()).token(jwt).build();
    }

    public User findUserByIdentifier(String identifier) {
        User user = userRepository.findByEmail(identifier);

        // Si aucun utilisateur trouvé par email, rechercher par username
        if (user == null) {
            user = userRepository.findByUsername(identifier);
        }
        return user;
    }
    @Override
    public User getConnectedUser()  {
        // Vérifier si l'utilisateur est authentifié
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new BadRequestException();
        }

        // Récupérer l'email de l'utilisateur actuellement connecté
        String identifier = authentication.getName();

        // Trouver l'utilisateur par son email
        User user = this.findUserByIdentifier(identifier);
        if (user==null){
            throw new UsernameNotFoundException("Utilisateur non trouve");
        }
        return user;
    }
    @Override
    public UserDto getConnectedUserInformation()  {
        // Vérifier si l'utilisateur est authentifié
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new BadRequestException();
        }

        // Récupérer l'email de l'utilisateur actuellement connecté
        String email = authentication.getName();

        // Trouver l'utilisateur par son email
        User user = userRepository.findByEmail(email);
        UserDto userDTO;
        if (user!=null) {
            // Créer un DTO ou retourner uniquement les informations nécessaires
             userDTO = new UserDto( user.getEmail(), user.getUsername(), " ",user.getCreatedAt(),user.getUpdatedAt());  // Exemple de DTO
        } else {
            throw new UsernameNotFoundException("Utilisateur non trouve");
        }
        return userDTO;
    }


    public User getUserById(int id) {
        return userRepository.findById(id).orElse(null);
    }
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void register(SignupRequest signUpRequest) {

        if (userDetailsService.isUserExists(signUpRequest.getEmail()) ) {
            throw new RuntimeException("On ne peut pas créer deux utilisateurs avec " +
                    "le même e-mail!");
        }


        // Create new user's account

        User user = User.builder().email(signUpRequest.getEmail()).username(signUpRequest.getUsername())
                .password(encoder.encode(signUpRequest.getPassword())).build();
        User savedUser = userRepository.save(user);

    }
    public User update(User user) {
        return userRepository.save(user);
    }
    public void delete(int id) {
        userRepository.deleteById(id);
    }
//TODO
    public User fetchUserByToken(String token) {
        String email = jwtServiceImpl.extractIdentifier(token);
        User user = userRepository.findByEmail(email);
        return  user;
    }

}
