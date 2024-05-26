package com.openclassrooms.mddapi.dtos;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TopicDto {

    private Long id;

    private String name;

    private String description;

    private List<Long> userIds;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}