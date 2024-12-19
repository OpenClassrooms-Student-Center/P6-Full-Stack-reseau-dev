package com.openclassrooms.mddapi.models;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor @NoArgsConstructor
@Entity
@Builder
public class Comment{
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        Long id;

        @NotNull
        @Size(min = 3)
        @Lob
        String content;

        @ManyToOne
        @JoinColumn(name = "post_id")
        Post post;

        @ManyToOne
        private User author;
}