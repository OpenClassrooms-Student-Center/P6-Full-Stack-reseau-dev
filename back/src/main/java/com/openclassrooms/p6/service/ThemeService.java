package com.openclassrooms.p6.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.p6.model.Themes;
import com.openclassrooms.p6.repository.ThemeRepository;

import lombok.Data;

/**
 * Service class for managing themes.
 */
@Data
@Service
public class ThemeService {
    /**
     * 
     * Theme repo to perform database operations on the {@link Themes} entity.
     */
    @Autowired
    private ThemeRepository themeRepository;

    /**
     * Retrieve all themes.
     *
     * @return A collection of all themes as a list.
     */
    public List<Themes> getThemes() {
        return themeRepository.findAll();
    }

    /**
     * Retrieves an theme by its unique identifier.
     *
     * @param themeId The identifier of the theme.
     * @return An Optional containing the theme if found, or empty if not.
     */
    public Optional<Themes> getThemeById(final Long themeId) {
        return themeRepository.findById(themeId);
    }
}
