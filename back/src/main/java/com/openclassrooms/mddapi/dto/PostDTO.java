package com.openclassrooms.mddapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

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
    @JsonProperty("topic_id")
    private Long topicId;
    @NotNull(message = "L'auteur de l'article ne peut pas être NULL.")
    @JsonProperty("user_id")
    private Long userId;
    @NotEmpty(message = "Le titre ne peut pas être vide.")
    @NotNull(message = "Le titre ne peut pas être NULL.")
    private String title;
    @NotEmpty(message = "Le contenu ne peut pas être vide.")
    @NotNull(message = "Le contenu ne peut pas être NULL.")
    private String content;
    @JsonProperty("created_at")
    private String createdAt;
    @JsonProperty("updated_at")
    private String updatedAt;
}
