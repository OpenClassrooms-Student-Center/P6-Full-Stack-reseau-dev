package com.openclassrooms.mddapi.dto;
import java.util.List;

import com.openclassrooms.mddapi.model.Themes;

import lombok.Data;
@Data
public class UserProfileDTO {
    private String username;
    private String email;
    private List<Themes> themes;
  
}