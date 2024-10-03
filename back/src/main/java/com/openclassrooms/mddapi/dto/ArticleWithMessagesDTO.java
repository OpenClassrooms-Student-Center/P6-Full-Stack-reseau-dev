package com.openclassrooms.mddapi.dto;
import java.util.List;
import lombok.Data;
@Data
public class ArticleWithMessagesDTO {
    private Long id;
    private String title;
    private String description;
    private List<MessageDTO> messages;
}