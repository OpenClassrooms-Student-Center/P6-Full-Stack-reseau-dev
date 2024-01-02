package com.openclassrooms.mddapi.model;

import lombok.Setter;
import lombok.Getter;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Builder;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "post")
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
/**
 * Stock post
 */
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_post;
    private String title;
    private LocalDateTime date;
    private String author;
    private String content;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "post")
    private List<Comments> comments = new ArrayList<>();

}
