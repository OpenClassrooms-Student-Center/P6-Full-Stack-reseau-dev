package com.openclassrooms.mddapi.configuration;

import com.openclassrooms.mddapi.dto.AuthorDto;
import com.openclassrooms.mddapi.model.UserModel;

public class AuthorMapper {
    public static AuthorDto toDTO(UserModel user) {
        if (user == null) {
            return null;
        }
        AuthorDto dto = new AuthorDto();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        return dto;
    }
}
