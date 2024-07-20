package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.exception.AuthException;
import com.openclassrooms.mddapi.exception.CommentException;
import com.openclassrooms.mddapi.exception.PostException;
import com.openclassrooms.mddapi.exception.RegistrationException;
import com.openclassrooms.mddapi.validation.Validation;
import jakarta.validation.Valid;
import com.openclassrooms.mddapi.dto.DBUserDTO;
import com.openclassrooms.mddapi.dto.ResponseDTO;
import com.openclassrooms.mddapi.dto.TokenDTO;
import com.openclassrooms.mddapi.jwt.IJWTService;
import com.openclassrooms.mddapi.service.user.IDBUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@Tag(name = "Auth", description = "Auth ressources")
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private IJWTService jwtService;
    @Autowired
    private IDBUserService dbUserService;

    @Operation(summary = "Register", description = "Register")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201",
            description = "Account successfully created",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ResponseDTO.class),
                examples = @ExampleObject(
                    value = "{\"message\": \"Compte crée avec succès\"}"
                )
            )
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Bad request",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = HashMap.class),
                examples = {
                    @ExampleObject(name = "Bad request", value = "{}")
                }
            )
        )
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/register", produces = "application/json")
    @SecurityRequirement(name = "")
    public ResponseDTO register(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @Content(
                examples = {
                    @ExampleObject(
                        name = "User sample",
                        summary = "User example",
                        value = "{\"email\": \"example@example.com\"," + "\"username\": \"JohnDoe\"," + "\"password\": \"Strong password\"}"
                    )
                }
            )
        )
        @RequestBody @Validated(Validation.Registration.class) DBUserDTO user,
        BindingResult result
    )
    {
        if(result.hasErrors()) {
            Map<String, String> errors = result.getFieldErrors().stream()
                    .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage, (existing, replacement) -> existing));
            throw new RegistrationException(errors.toString());
        }
        return dbUserService.register(user);
    }

    @Operation(summary = "Login", description = "Login to MDD")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Login successful",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = TokenDTO.class),
                examples = @ExampleObject(
                    value = "{\"token\": \"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c\"}"
                )
            )
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Bad Request",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ResponseDTO.class),
                examples = @ExampleObject(
                        name = "Bad request",
                        value = "{\"message\": \"error\"}"
                )
            )
        )
    })
    @ResponseStatus(HttpStatus.OK)
    @PostMapping(value = "/login", produces = "application/json")
    @SecurityRequirement(name = "")
    public TokenDTO login(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @Content(
                examples = {
                        @ExampleObject(
                            name = "Login with email",
                            summary = "User example with email",
                            value = "{\"login\": \"example@test.com\"," + "\"password\": \"Strong password\"}"
                        ),
                        @ExampleObject(
                            name = "Login with username",
                            summary = "User example with username",
                            value = "{\"login\": \"username\"," + "\"password\": \"Strong password\"}"
                        )
                }
            )
        )
        @RequestBody @Validated(Validation.Authentication.class) DBUserDTO user,
        BindingResult result
    )
    {
        if(result.hasErrors()) {
            Map<String, String> errors = result.getFieldErrors().stream()
                    .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage, (existing, replacement) -> existing));
            throw new AuthException(errors.toString());
        }
       return dbUserService.login(user);
    }
}
