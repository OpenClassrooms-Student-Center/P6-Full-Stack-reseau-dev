package com.openclassrooms.mddapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TopicDto {
    private Long id;
    private String subject;
    private String description;
    private boolean isUserSubscribed;

}
