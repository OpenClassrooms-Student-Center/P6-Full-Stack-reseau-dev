package com.openclassrooms.mddapi.models;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor @NoArgsConstructor
@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    @ManyToOne
    private User author;
    @Lob
    private String content;

    @ManyToOne
    private Topic topic;

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

}
