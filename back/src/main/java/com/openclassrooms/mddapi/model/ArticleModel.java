package com.openclassrooms.mddapi.model;

import com.openclassrooms.mddapi.dto.AuthorDto;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "article")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ArticleModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "author_id", nullable = false)
    private UserModel author;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "theme_id", nullable = false)
    private ThemeModel theme;

    @Temporal(TemporalType.TIMESTAMP)
    private Date publishedDate;

    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CommentModel> comments;
}
