package com.openclassrooms.mddapi.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Data
@Entity
@Table(name = "comment")
@NoArgsConstructor
@AllArgsConstructor
public class CommentModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String content;

    @ManyToOne
    @JoinColumn(name = "article_id", nullable = false)
    private ArticleModel article;

    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    private UserModel author;
}
