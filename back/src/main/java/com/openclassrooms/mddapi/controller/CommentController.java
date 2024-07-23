package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.dto.*;
import com.openclassrooms.mddapi.exception.CommentException;
import com.openclassrooms.mddapi.service.comment.ICommentService;
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
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@Tag(name = "Comments", description = "Comments resources")
@RequestMapping("/comments")
public class CommentController {

    @Autowired
    private ICommentService commentService;

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
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/post/{postId}", produces = "application/json")
    @SecurityRequirement(name = "bearer")
    public List<CommentDTO> getPostComments(@PathVariable Long postId) {
        return commentService.getCommentsByPost(postId);
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
                            name = "Comments successfully created",
                            value =
                                "{" +
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
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/posts", produces = "application/json")
    @SecurityRequirement(name = "bearer")
    public ResponseDTO createComment(
            Principal user,
            @RequestBody @Validated CommentDTO commentDTO,
            BindingResult result
    )
    {
        if(result.hasErrors()) {
            Map<String, String> errors = result.getFieldErrors().stream()
                    .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage, (existing, replacement) -> existing));
            throw new CommentException(errors.toString());
        }
        return commentService.createComment(commentDTO, user);
    }

}
