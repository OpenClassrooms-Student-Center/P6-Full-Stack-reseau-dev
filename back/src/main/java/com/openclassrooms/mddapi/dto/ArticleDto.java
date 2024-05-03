package com.openclassrooms.mddapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleDto {

    @NotBlank
    @Size(max = 50)
    private String titre;

    @NotNull
    private Long theme_id;

    @NotBlank
    private String description;

    private LocalDateTime date;

    private Long user_id;
}
