package com.openclassrooms.mddapi.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.openclassrooms.mddapi.model.DBUser;
import com.openclassrooms.mddapi.model.Post;
import com.openclassrooms.mddapi.validation.Validation;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentDTO {
    private Long id;
    @NotNull(message = "Le champ id du post ne peut pas être NULL.")
    private Long postId;
    @NotNull(message = "Le champ utilisateur ne peut pas être NULL.")
    private DBUserDTO user;
    @NotEmpty(message = "Le contenu ne peut pas être vide.")
    @NotNull(message = "Le contenu ne peut pas être NULL.")
    private String content;
    private String createdAt;
    private String updatedAt;
}
