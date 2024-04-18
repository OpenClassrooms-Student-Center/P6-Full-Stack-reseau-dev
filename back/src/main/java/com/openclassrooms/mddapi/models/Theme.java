package com.openclassrooms.mddapi.models;

import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "THEMES")
@EntityListeners(AuditingEntityListener.class)
@Data
@Accessors(chain = true)
@EqualsAndHashCode(of = {"id"})
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Theme {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long theme_id;

    @NonNull
    private String titre;

    @NonNull
    private String description;

    @OneToMany(mappedBy = "theme")
    private Set<Article> article = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "themes")
    private Set<User> users = new HashSet<>();


}
