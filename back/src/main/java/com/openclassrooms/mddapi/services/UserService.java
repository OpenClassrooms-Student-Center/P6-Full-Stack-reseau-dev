package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.exception.BadRequestException;
import com.openclassrooms.mddapi.exception.NotFoundException;
import com.openclassrooms.mddapi.models.Theme;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.repository.ThemeRepository;
import com.openclassrooms.mddapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ThemeRepository themeRepository;

    public User update(Long user_id, User user) {
        user.setUser_id(user_id);
        return userRepository.save(user);
    }

    public User findById(Long id) {
        return this.userRepository.findById(id).orElse(null);
    }

    public void follow(Long themeId, Long userId) {
        Theme theme = this.themeRepository.findById(themeId).orElse(null);
        User user = this.userRepository.findById(userId).orElse(null);
        if (theme == null || user == null) {
            throw new NotFoundException();
        }

        boolean alreadyFollow = user.getThemes().stream().anyMatch(o -> o.getThemeId().equals(themeId));
        if(alreadyFollow) {
            throw new BadRequestException();
        }

        user.getThemes().add(theme);

        this.userRepository.save(user);
    }

    public void unFollow(Long id, Long userId) {
        User user = this.userRepository.findById(userId).orElse(null);

        if (user == null) {
            throw new NotFoundException();
        }

        boolean alreadyFollow = user.getThemes().stream().anyMatch(o -> o.getThemeId().equals(id));
        if(!alreadyFollow) {
            throw new BadRequestException();
        }

        user.setThemes(user.getThemes().stream().filter(theme -> !theme.getThemeId().equals(id)).collect(Collectors.toSet()));

        this.userRepository.save(user);
    }
}
