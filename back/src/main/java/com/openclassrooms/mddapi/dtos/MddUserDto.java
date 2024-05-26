package com.openclassrooms.mddapi.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.experimental.Accessors;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Accessors(chain = true)
public class MddUserDto {

      private Long id;

      @Size(max = 50)
      @Email
      private String email;

      @Size(max = 20)
      private String username;

      @Size(max = 120)
      private String password;

      private List<Long> commentIds;

      private List<Long> postIds;

      private List<Long> topicIds;

      private LocalDateTime createdAt;

      private LocalDateTime updatedAt;
}