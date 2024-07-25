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
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;
    @NotNull(message = "Le topic ne peut pas être NULL.")
    private Long topicId;
    private Long userId;
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
