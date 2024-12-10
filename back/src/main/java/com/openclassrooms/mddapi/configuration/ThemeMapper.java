package com.openclassrooms.mddapi.configuration;

import com.openclassrooms.mddapi.dto.ThemeDto;
import com.openclassrooms.mddapi.model.ThemeModel;

public class ThemeMapper {

    public static ThemeDto toDTO(ThemeModel theme) {
        if (theme == null) {
            return null;
        }

        ThemeDto dto = new ThemeDto();
        dto.setId(theme.getId());
        dto.setName(theme.getName());
        dto.setContent(theme.getContent());
        return dto;
    }
}
