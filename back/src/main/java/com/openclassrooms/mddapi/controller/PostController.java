package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.dto.PostDTO;
import com.openclassrooms.mddapi.dto.PostsDTO;
import com.openclassrooms.mddapi.dto.ResponseDTO;
import com.openclassrooms.mddapi.service.post.IPostService;
import com.openclassrooms.mddapi.service.user.IDBUserService;
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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@Tag(name = "Posts", description = "Posts resources")
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private IPostService postService;

    @Autowired
    private IDBUserService dbUserService;

    @Operation(summary = "Get posts", description = "Retrieve all posts")
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "200",
                description = "Rentals successfully retrieved",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = PostDTO.class),
                    examples = @ExampleObject(
                        value =
                            "[" +
                                "{" +
                                    "\"id\": 1," +
                                    "\"title\": \"Post 1\"," +
                                    "\"content\": \"Content 1\"," +
                                    "\"created_at\": \"2012/12/02\"," +
                                    "\"topic_id\": \"1\"," +
                                    "\"user_id\": \"3\"," +
                                "}," +
                                "{" +
                                "\"id\": 2," +
                                "\"title\": \"Post 2\"," +
                                "\"content\": \"Content 2\"," +
                                "\"created_at\": \"2012/12/02\"," +
                                "\"topic_id\": \"5\"," +
                                "\"user_id\": \"4\"," +
                                "}" +
                            "]"
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
                        value =
                            "{" +
                                "\"message\": \"Unauthorized\"" +
                            "}"
                    )
                )
            ),
            @ApiResponse(
                responseCode = "400",
                description = "Bad request",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(type = ""),
                    examples = @ExampleObject(
                            name = "Bad request",
                            value = "{" +
                                    "\"message\": \"{{Error message}}\"" +
                                    "}"
                    )
                )
            )
        }
    )
    @GetMapping(value = "", produces = "application/json")
    @SecurityRequirement(name = "bearer")
    public PostsDTO getPosts() {
        return PostsDTO.builder().posts(postService.getPosts()).build();
    }

    @Operation(summary = "Get post", description = "Retrieved a specified post")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Post successfully retrieved",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = PostDTO.class),
                examples = @ExampleObject(
                    value =
                        "{" +
                            "\"id\": 1," +
                            "\"title\": \"Post 1\"," +
                            "\"content\": \"Content 1\"," +
                            "\"created_at\": \"2012/12/02\"," +
                            "\"topic_id\": \"1\"," +
                            "\"user_id\": \"3\"," +
                        "},"
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
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Bad request",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(type = ""),
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
    @GetMapping(value = "/{id}", produces = "application/json")
    @SecurityRequirement(name = "bearer")
    public PostDTO getPost(@PathVariable(value = "id") Long id) {
        return postService.getPost(id);
    }

    @Operation(summary = "Create a post", description = "Create a new post")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Post created",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ResponseDTO.class),
                                    examples = @ExampleObject(
                                            name = "Post created",
                                            value = "{\"message\": \"Post created !\"}"
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
                    ),
                    @ApiResponse(
                        responseCode = "400",
                        description = "Bad request",
                        content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(type = ""),
                            examples = @ExampleObject(
                                name = "Bad request",
                                value =
                                    "{" +
                                        "\"message\": \"{{Error message}}\"" +
                                    "}"
                            )
                        )
                    )
            }
    )
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "", produces = "application/json")
    @SecurityRequirement(name = "bearer")
    public ResponseDTO createPost(
            Principal user,
            @RequestBody @Valid PostDTO postDTO
    )
    {
        postService.createPost(dbUserService.findByEmail(user.getName()), postDTO);
        return new ResponseDTO("Post created !");
    }

}
