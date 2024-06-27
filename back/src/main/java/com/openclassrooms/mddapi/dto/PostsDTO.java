package com.openclassrooms.mddapi.dto;

import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostsDTO {
    private List<PostDTO> posts;
}
