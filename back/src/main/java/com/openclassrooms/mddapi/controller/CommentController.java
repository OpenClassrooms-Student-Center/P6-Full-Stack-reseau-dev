package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.dto.*;
import com.openclassrooms.mddapi.service.comment.ICommentService;
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
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@Tag(name = "Comments", description = "Comments resources")
@RequestMapping("/comments")
public class CommentController {

    @Autowired
    private ICommentService commentService;

    @Autowired
    private IDBUserService dbUserService;

    @Operation(summary = "Get comments by post", description = "Retrieve all comments by post")
    @ApiResponses(
            value = {
                    @ApiResponse(
                        responseCode = "200",
                        description = "Comments successfully retrieved",
                        content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDTO.class),
                            examples = @ExampleObject(
                                name = "Comments successfully retrieved",
                                value=
                                    "[" +
                                        "{" +
                                            "\"comments\": [" +
                                            "{" +
                                            "\"id\": 1," +
                                            "\"content\": \"Content 1\"," +
                                            "\"created_at\": \"2012/12/02\"," +
                                            "\"post_id\": 1," +
                                            "\"user_id\": 3" +
                                        "}," +
                                        "{" +
                                        "\"id\": 2," +
                                        "\"content\": \"Content 2\"," +
                                        "\"created_at\": \"2012/12/02\"," +
                                        "\"post_id\": 1," +
                                        "\"user_id\": 4" +
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
    @GetMapping(value = "/posts/{postId}", produces = "application/json")
    @SecurityRequirement(name = "bearer")
    public CommentsDTO getPostComments(@PathVariable Long postId) {
        return new CommentsDTO(commentService.getCommentsByPostId(postId));
    }


    @Operation(summary = "Create a comment", description = "Create a new comment")
    @ApiResponses(
            value = {
                @ApiResponse(
                    responseCode = "200",
                    description = "Comment created",
                    content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = ResponseDTO.class),
                        examples = @ExampleObject(
                                name = "Comments successfully retrieved",
                                value = "{" +
                                            "\"message\": \"Comment created !\"" +
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
    @PostMapping(value = "", produces = "application/json")
    @SecurityRequirement(name = "bearer")
    public ResponseDTO createComment(
            Principal user,
            @ModelAttribute CommentDTO commentDTO
    )
    {
        commentService.createComment(dbUserService.findByEmail(user.getName()), commentDTO);
        return new ResponseDTO("Comment created !");
    }

}
