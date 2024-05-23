package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.dto.ThemeDto;
import com.openclassrooms.mddapi.dto.ThemeUpdateDto;
import com.openclassrooms.mddapi.dto.UserDto;
import com.openclassrooms.mddapi.models.Theme;
import com.openclassrooms.mddapi.services.ThemeService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/theme")
@Log4j2
public class ThemeController {

    @Autowired
    ThemeService themeService;

    @GetMapping()
    public ResponseEntity<?> findAll() {
        List<Theme> themeList = this.themeService.findAll();
        List<ThemeDto> themeListDto= new ArrayList<>();

        for(Theme theme : themeList) {
            ThemeDto themeDto= new ThemeDto();
            themeDto.setThemeId(theme.getThemeId());
            themeDto.setDescription(theme.getDescription());
            themeDto.setTitre(theme.getTitre());
            themeListDto.add(themeDto);
        }

        return ResponseEntity.ok().body(themeListDto);
    }

    @GetMapping("/{themeId}")
    public ResponseEntity<?> findById(@PathVariable("themeId") String id) {
        try {
            Theme theme = this.themeService.findById(Long.valueOf(id));
            if (theme == null) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok().body(theme);
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<?> update(@PathVariable("id") String id, @Valid @RequestBody ThemeDto themeDto) {

        Theme theme = new Theme();

        try {
            theme = this.themeService.update(Long.parseLong(id),theme);

            theme.setDescription(themeDto.getDescription());
            theme.setTitre(themeDto.getTitre());
            theme.setFollow(themeDto.getFollow());


            return ResponseEntity.ok().body(theme);
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
