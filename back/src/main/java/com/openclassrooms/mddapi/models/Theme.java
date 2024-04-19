package com.openclassrooms.mddapi.models;

import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
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

//    @OneToMany(mappedBy = "theme")
//    private List<Article> articles;

//    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "themes")
//    private List<User> users;


}
