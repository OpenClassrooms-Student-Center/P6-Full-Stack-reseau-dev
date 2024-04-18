package com.openclassrooms.mddapi.models;

import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "ARTICLES")
@EntityListeners(AuditingEntityListener.class)
@Data
@Accessors(chain = true)
@EqualsAndHashCode(of = {"id"})
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long article_id;

    @NonNull
    private String titre;

    @NonNull
    private String auteur;

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @NonNull
    private String contenu;

    @ManyToOne(cascade=CascadeType.PERSIST)
    @JoinColumn(name = "theme_id", referencedColumnName="theme_id")
    private Theme theme;

    @ElementCollection(targetClass=String.class)
    private List<String> commentaires;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "articles")
    private Set<User> users = new HashSet<>();


}
