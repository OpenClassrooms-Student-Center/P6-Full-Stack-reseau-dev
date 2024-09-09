package com.openclassrooms.p6.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.openclassrooms.p6.model.Themes;
import com.openclassrooms.p6.payload.response.SingleThemeResponse;

/**
 * Interface for mapping between {@link Themes} and {@link SingleThemeResponse}.
 */
@Mapper(componentModel = "spring")
public interface ThemeMapper {

    /**
     * Maps a list of {@link Themes} objects to a list of
     * {@link SingleThemeResponse} objects.
     *
     * @param themes The {@link Themes} objects to be converted.
     * @return The corresponding list of {@link SingleThemeResponse} objects.
     */
    Iterable<SingleThemeResponse> toDtoThemes(List<Themes> themes);

    /**
     * Maps a {@link Themes} object to a {@link SingleThemeResponse} object.
     *
     * @param theme The {@link Themes} object to be converted.
     * @return The corresponding {@link SingleThemeResponse} object.
     */
    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "title", source = "title"),
            @Mapping(target = "description", source = "description")
    })
    SingleThemeResponse toDtoTheme(Themes theme);
}