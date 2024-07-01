package com.openclassrooms.mddapi.controller;


import com.openclassrooms.mddapi.dto.DBUserDTO;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "User", description = "User resources")
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IDBUserService dbUserService;

    @Operation(summary = "Get user", description = "Retrieve a specific user")
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "200",
                description = "Successful login",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = DBUserDTO.class),
                    examples = @ExampleObject(
                        name = "User example",
                        value = "{" +
                            "\"id\": 99," +
                            "\"name\": \"John DOE\"," +
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
                    schema = @Schema(type = ""),
                    examples = @ExampleObject(
                        name = "Unauthorized",
                        value = ""
                    )
                )
            )
        }
    )
    @GetMapping(value = "/{id}", produces = "application/json")
    @SecurityRequirement(name = "bearer")
    public DBUserDTO getUser(@PathVariable(value = "id") Integer userId) {
        return dbUserService.findById(userId);
    }
}
