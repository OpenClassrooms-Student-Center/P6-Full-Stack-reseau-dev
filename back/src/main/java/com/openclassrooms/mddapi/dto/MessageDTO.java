package com.openclassrooms.mddapi.dto;
import lombok.Data;
@Data
public class MessageDTO {
    private Integer id; 
    private String userUsername;
    private String message;
 
}