package com.openclassrooms.mddapi.controller;


import com.openclassrooms.mddapi.dto.DBUserDTO;
import com.openclassrooms.mddapi.dto.ResponseDTO;
import com.openclassrooms.mddapi.dto.TokenDTO;
import com.openclassrooms.mddapi.exception.MeException;
import com.openclassrooms.mddapi.exception.PostException;
import com.openclassrooms.mddapi.jwt.IJWTService;
import com.openclassrooms.mddapi.service.user.IDBUserService;
import com.openclassrooms.mddapi.validation.Validation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@Tag(name = "User", description = "User resources")
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IDBUserService dbUserService;

    @Operation(summary = "Profile", description = "Get the user profile")
    @ApiResponses(value = {
            @ApiResponse(
                responseCode = "200",
                description = "Get user details",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = DBUserDTO.class),
                    examples = @ExampleObject(
                        name = "User example",
                        value =
                            "{\"" +
                                "id\": 99," +
                                "\"username\": \"JohnDoe\"," +
                                "\"email\": \"example@chatop.com\"," +
                                "\"created_at\": \"2024/04/29\"," +
                                "\"updated_at\": \"2024/04/29\"" +
                            "}"
                    )
                )
            ),
            @ApiResponse(
                responseCode = "401",
                description = "Unauthorized",
                content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(
                        name = "Unauthorized",
                        value = ""
                    )
                )
            ),
            @ApiResponse(
                responseCode = "400",
                description = "Bad request",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ResponseDTO.class),
                    examples = @ExampleObject(
                        name = "Bad request",
                        value =
                            "{" +
                                "\"message\": \"{{Error message}}\"" +
                            "}"
                    )
                )
            )
    })
    @ResponseStatus(HttpStatus.OK)
    @SecurityRequirement(name = "bearer")
    @GetMapping(value = "/me", produces = "application/json")
    public DBUserDTO info(Principal user) {
        return dbUserService.findByEmail(user.getName());
    }

    @Operation(summary = "Update profile", description = "Update profile")
    @ApiResponses(value = {
            @ApiResponse(
                responseCode = "200",
                description = "Update user details",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ResponseDTO.class),
                    examples = @ExampleObject(
                            name = "User example",
                            value =
                                "{" +
                                    "\"message\": \"User updated !\"" +
                                "}"
                    )
                )
            ),
            @ApiResponse(
                responseCode = "401",
                description = "Unauthorized",
                content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(
                            name = "Unauthorized",
                            value = ""
                    )
                )
            ),
            @ApiResponse(
                responseCode = "400",
                description = "Bad request",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ResponseDTO.class),
                    examples = @ExampleObject(
                            name = "Bad request",
                            value =
                                "{" +
                                    "\"message\": \"{{Error message}}\"" +
                                "}"
                    )
                )
            )
    })
    @PutMapping(value = "/me", produces = "application/json")
    @SecurityRequirement(name = "bearer")
    public DBUserDTO updateInfo(
            @RequestBody @Validated(Validation.Me.class) DBUserDTO newUser,
            Principal user,
            BindingResult result
    ) {
        if(result.hasErrors()) {
            Map<String, String> errors = result.getFieldErrors().stream()
                    .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage, (existing, replacement) -> existing));
            throw new MeException(errors.toString());
        }
        return dbUserService.update(newUser, user);
    }

}
