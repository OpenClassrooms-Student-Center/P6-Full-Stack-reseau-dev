package com.openclassrooms.p6.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.p6.exception.ApiException;
import com.openclassrooms.p6.exception.GlobalExceptionHandler;
import com.openclassrooms.p6.mapper.UserMapper;
import com.openclassrooms.p6.model.Users;
import com.openclassrooms.p6.payload.request.LoginRequest;
import com.openclassrooms.p6.payload.request.RegisterRequest;
import com.openclassrooms.p6.payload.response.AuthResponse;
import com.openclassrooms.p6.payload.response.UserInfoResponse;
import com.openclassrooms.p6.service.UserService;
import com.openclassrooms.p6.utils.JwtUtil;

import jakarta.validation.Valid;

/**
 * This is the AuthController class that handles user authentication and
 * registration.
 * It is responsible for registering a new user, logging in an existing user,
 * and performing various checks.
 * The class is annotated with {@code @RestController} and
 * {@code @RequestMapping} to define the
 * API endpoints.
 * It also uses various dependencies such as UserService, UserMapper, and
 * JwtUtil.
 */
@CrossOrigin("*")
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    /**
     * UserService to manage user-related operations.
     */
    @Autowired
    private UserService userService;

    /**
     * UserMapper for converting between entity and DTO types.
     */
    @Autowired
    private UserMapper userMapper;

    /**
     * Registers a new user.
     *
     * @param request The registration request containing user details.
     * @return ResponseEntity<AuthResponse> A JWT if registration is successful.
     */
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request,
            BindingResult bindingResult) {
        try {
            checkBodyPayloadErrors(bindingResult);

            checkIfUsernameIsInUse(request.username());

            checkIfEmailIsInUse(request.email());

            Users user = userService.saveUserBySignUp(request);

            UserInfoResponse userDto = userMapper.toDtoUser(user);

            String jwtToken = JwtUtil.generateJwtToken(userDto.id());

            AuthResponse authResponse = new AuthResponse(jwtToken, userDto.id(), userDto.username(),
                    userDto.email());

            return ResponseEntity.status(HttpStatus.CREATED).body(authResponse);

        } catch (ApiException e) {
            return GlobalExceptionHandler.handleApiException(e);
        }
    }

    /**
     * Logs in an existing user.
     *
     * @param request The login request containing user credentials.
     * @return ResponseEntity<AuthResponse> A JWT if login is successful.
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request, BindingResult bindingResult) {
        try {
            checkBodyPayloadErrors(bindingResult);

            Users user = getUserFromIdentifier(request.identifier());

            checkUserPassword(request.password(), user);

            UserInfoResponse userEntity = userMapper.toDtoUser(user);

            String jwtToken = JwtUtil.generateJwtToken(userEntity.id());

            AuthResponse authResponse = new AuthResponse(jwtToken, userEntity.id(), userEntity.username(),
                    userEntity.email());

            return ResponseEntity.status(HttpStatus.OK).body(authResponse);
        } catch (ApiException ex) {
            return GlobalExceptionHandler.handleApiException(ex);
        }
    }

    /**
     * Checks if there are any payload errors in the request body.
     *
     * @param bindingResult The BindingResult object that holds the validation
     *                      errors.
     */
    private void checkBodyPayloadErrors(BindingResult bindingResult) {
        Boolean payloadIsInvalid = bindingResult.hasErrors();
        if (payloadIsInvalid) {
            GlobalExceptionHandler.handlePayloadError("Bad request", bindingResult, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Checks if an username is already registered in the system.
     *
     * @param username The username to check if it is already registered.
     */
    private void checkIfUsernameIsInUse(String username) {
        Boolean hasAlreadyRegistered = userService.isUsernameInUse(username);
        if (hasAlreadyRegistered) {
            GlobalExceptionHandler.handleLogicError("Username is already in use", HttpStatus.CONFLICT);
        }
    }

    /**
     * Checks if an email is already registered in the system.
     *
     * @param email The email to check if it is already registered.
     */
    private void checkIfEmailIsInUse(String email) {
        Boolean hasAlreadyRegistered = userService.isEmailInUse(email);
        if (hasAlreadyRegistered) {
            GlobalExceptionHandler.handleLogicError("Email is already in use", HttpStatus.CONFLICT);
        }
    }

    /**
     * Checks if the provided password is correct for the given user.
     *
     * @param requestPassword The password to check.
     * @param user            The user to check the password against.
     */
    private void checkUserPassword(String requestPassword, Users user) {
        Boolean passwordIsIncorrect = !userService.isPasswordValid(requestPassword, user);
        if (passwordIsIncorrect) {
            GlobalExceptionHandler.handleLogicError("Password is incorrect", HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * Checks if the provided username or email in the login request is valid and
     * returns the user
     *
     * @param usernameOrEmail The username or email to check.
     * @throws ApiException If the username or email is invalid.
     */
    private Users getUserFromIdentifier(String usernameOrEmail) {
        Optional<Users> userFromEmail = userService.getUserByEmail(usernameOrEmail);
        Optional<Users> userFromUsername = userService.getUserByUsername(usernameOrEmail);

        Boolean identifierIsInvalid = userFromEmail.isEmpty() && userFromUsername.isEmpty();
        if (identifierIsInvalid) {
            GlobalExceptionHandler.handleLogicError("Invalid username/email", HttpStatus.UNAUTHORIZED);
        }

        return userFromEmail.isPresent() ? userFromEmail.get() : userFromUsername.get();
    }
}
