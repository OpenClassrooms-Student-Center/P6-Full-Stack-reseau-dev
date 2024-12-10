package com.openclassrooms.mddapi.dto;

import jakarta.persistence.*;
import lombok.*;
@Entity
@Data
@Table(name = "theme")
@Getter
@Setter
public class ThemeDto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String content;
    private boolean isSubscribed;
}
