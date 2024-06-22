package com.openclassrooms.mddapi.controller;

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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.validation.annotation.*;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;


@RestController
@Validated
@Tag(name = "Auth", description = "Auth ressources")
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private IJWTService jwtService;
    @Autowired
    private IDBUserService dbUserService;

    @Operation(summary = "Register", description = "Register to Chatop")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "User successfully connected",
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
    @PostMapping(value = "/register", produces = "application/json")
    @SecurityRequirement(name = "")
    public TokenDTO register(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @Content(
                examples = {
                    @ExampleObject(
                        name = "User sample",
                        summary = "User example",
                        value = "{\"email\": \"example@chatop.com\"," + "\"name\": \"John Doe\"," + "\"password\": \"Strong password\"}"
                    )
                }
            )
        )
        @Valid @RequestBody DBUserDTO user,
        Errors errors
    )
    {
        dbUserService.create(user);
        UsernamePasswordAuthenticationToken authReq = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
        Authentication auth = authenticationManager.authenticate(authReq);
        return new TokenDTO(jwtService.generateToken(auth));
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
            responseCode = "401",
            description = "Unauthorized",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ResponseDTO.class),
                examples = @ExampleObject(
                        name = "Unauthorized",
                        value = "{\"message\": \"error\"}"
                )
            )
        )
    })
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
        @RequestBody DBUserDTO user
    )
    {

        String usernameOrEmail = user.getEmail();
        String password = user.getPassword();

        if (usernameOrEmail.contains("@")) {
            Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(usernameOrEmail, password));
            return new TokenDTO(jwtService.generateToken(auth));
        } else {
            // Assuming you have a method in your service to find user by username
            DBUserDTO userByUsername = dbUserService.findByUsername(usernameOrEmail);
            Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userByUsername.getEmail(), password));
            return new TokenDTO(jwtService.generateToken(auth));
        }

    }

    @Operation(summary = "Profile", description = "Get the user profile")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "User profile",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = DBUserDTO.class),
                examples = @ExampleObject(
                    name = "User example",
                    value = "{\"id\": 99,\"name\": \"John DOE\",\"username\": \"JohnDoe\",\"email\": \"example@chatop.com\",\"created_at\": \"2024/04/29\",\"updated_at\": \"2024/04/29\"}"
                )
            )
        ),
        @ApiResponse(
            responseCode = "401",
            description = "Unauthorized",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = HashMap.class),
                examples = @ExampleObject(
                        name = "Unauthorized",
                        value = "{}"
                )
            )
        )
    })
    @GetMapping(value = "/me", produces = "application/json")
    @SecurityRequirement(name = "bearer")
    public DBUserDTO info(Principal user) {
        return dbUserService.findByEmail(user.getName());
    }
}
