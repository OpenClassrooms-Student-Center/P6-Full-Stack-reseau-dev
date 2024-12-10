package com.openclassrooms.mddapi.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Data
@Entity
@Table(name = "user")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;
    @Column
    private String username;
    @Column
    private String email;
    @Column
    private String password;
    @ManyToMany
    @JoinTable(
            name = "UserThemeSubscription",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "theme_id")
    )
    private Set<ThemeModel> themes;
}
