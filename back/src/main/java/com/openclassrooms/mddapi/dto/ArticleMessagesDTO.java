package com.openclassrooms.mddapi.dto;
import java.util.List;
import lombok.Data;
@Data
public class ArticleMessagesDTO {
    
    private Long articleId;
    private Long userId;
    private String title;
    private String description;
    private List<MessageDTO> messages;
}