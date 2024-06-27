package com.openclassrooms.mddapi.service.post;

import com.openclassrooms.mddapi.dto.DBUserDTO;
import com.openclassrooms.mddapi.dto.PostDTO;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;

public interface IPostService {
    PostDTO getPost(Long id) throws EntityNotFoundException;
    List<PostDTO> getPosts();
    void createPost(DBUserDTO currentUser, PostDTO postDTO) throws RuntimeException;
}
