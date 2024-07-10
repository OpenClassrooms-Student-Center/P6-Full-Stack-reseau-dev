package com.openclassrooms.mddapi.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.openclassrooms.mddapi.validation.ValidationUserGroup;
import jakarta.validation.constraints.Email;
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
    private Integer id;

    @NotNull(groups = {ValidationUserGroup.RegistrationUser.class}, message = "Username is required")
    @NotEmpty(groups = {ValidationUserGroup.RegistrationUser.class}, message = "Username is required")
    private String username;

    @JsonAlias({"login"})
    @NotNull(groups = {ValidationUserGroup.AuthenticationUser.class, ValidationUserGroup.RegistrationUser.class}, message = "Email is required")
    private String email;

    @NotNull(groups = {ValidationUserGroup.AuthenticationUser.class, ValidationUserGroup.RegistrationUser.class}, message = "Password is required")
    @NotEmpty(groups = {ValidationUserGroup.AuthenticationUser.class, ValidationUserGroup.RegistrationUser.class}, message = "Password is required")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @JsonProperty("created_at")
    private String createdAt;

    @JsonProperty("updated_at")
    private String updatedAt;
}
