package com.openclassrooms.mddapi.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@Entity
@Table(name = "theme")
@Getter
@Setter
public class ThemeModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String content;

    @OneToMany(mappedBy = "theme", cascade = CascadeType.ALL)
    private List<ArticleModel> articles;
}
