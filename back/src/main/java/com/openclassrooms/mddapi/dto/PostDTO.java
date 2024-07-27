package com.openclassrooms.mddapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.openclassrooms.mddapi.model.DBUser;
import com.openclassrooms.mddapi.model.Topic;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostDTO {
    private Long id;
    @NotNull(message = "Le topic ne peut pas être NULL.")
    private TopicDTO topic;
    @NotNull(message = "Le champ utilisateur ne peut pas être NULL.")
    private DBUserDTO user;
    @NotEmpty(message = "Le titre ne peut pas être vide.")
    @NotNull(message = "Le titre ne peut pas être NULL.")
    private String title;
    @NotEmpty(message = "Le contenu ne peut pas être vide.")
    @NotNull(message = "Le contenu ne peut pas être NULL.")
    private String content;
    private List<CommentDTO> comments;
    private String createdAt;
    private String updatedAt;
}
