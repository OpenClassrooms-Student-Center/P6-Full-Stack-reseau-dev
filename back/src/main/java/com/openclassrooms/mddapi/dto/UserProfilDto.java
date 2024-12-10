package com.openclassrooms.mddapi.dto;

import com.openclassrooms.mddapi.model.ArticleModel;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserProfilDto {
    private String username;
    private String email;
    @OneToMany(mappedBy = "theme", cascade = CascadeType.ALL)
    private List<ThemeDto> themes;
    public UserProfilDto(String username, String email) {
        this.username = username;
        this.email = email;
    }
}
