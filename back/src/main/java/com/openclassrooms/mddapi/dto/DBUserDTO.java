package com.openclassrooms.mddapi.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
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
public class DBUserDTO {
    private Long id;

    @NotNull(
            groups = {
                    Validation.Me.class,
            },
            message = "Le champ nom d'utilisateur ne peut pas être NULL."
    )
    @NotEmpty(
            groups = {
                    Validation.Me.class,
            },
            message = "Le champ nom d'utilisateur ne peut pas être vide."
    )
    @NotNull(
            groups = {
                    Validation.Registration.class,
            },
            message = "Le champ nom d'utilisateur ne peut pas être NULL."
    )
    @NotEmpty(
            groups = {
                    Validation.Registration.class,
            },
            message = "Le champ nom d'utilisateur ne peut pas être vide."
    )
    private String username;

    @JsonAlias({"login"})
    @NotNull(
            groups = {
                    Validation.Me.class,
            },
            message = "Le champ email ne peut pas être NULL."
    )
    @NotEmpty(
            groups = {
                    Validation.Me.class,
            },
            message = "Le champ email ne peut pas être vide."
    )
    @NotNull(
            groups = {
                    Validation.Registration.class,
            },
            message = "Le champ email ne peut pas être NULL."
    )
    @NotEmpty(
            groups = {
                    Validation.Registration.class,
            },
            message = "Le champ email ne peut pas être vide."
    )
    @NotNull(
            groups = {
                    Validation.Authentication.class,
            },
            message = "Le champ login ne peut pas être NULL."
    )
    @NotEmpty(
            groups = {
                    Validation.Authentication.class,
            },
            message = "Le champ login ne peut pas être vide."
    )
    private String email;

    @NotNull(
            groups = {
                    Validation.Registration.class,
                    Validation.Authentication.class
            },
            message = "Le champ mot de passe ne peut pas être NULL."
    )
    @NotEmpty(
            groups = {
                    Validation.Registration.class,
                    Validation.Authentication.class
            },
            message = "Le champ mot de passe ne peut pas être vide."
    )
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @JsonProperty("created_at")
    private String createdAt;

    @JsonProperty("updated_at")
    private String updatedAt;
}
