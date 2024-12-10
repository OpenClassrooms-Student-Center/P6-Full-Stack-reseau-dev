package com.openclassrooms.mddapi.dto;

import lombok.*;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {
    private Long id;
    private String content;
    private Date publishedDate;
    private UserProfilDto author;
}
