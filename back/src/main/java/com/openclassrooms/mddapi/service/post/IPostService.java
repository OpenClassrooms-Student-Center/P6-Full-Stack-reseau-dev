package com.openclassrooms.mddapi.service.post;

import com.openclassrooms.mddapi.dto.PostDTO;
import com.openclassrooms.mddapi.dto.ResponseDTO;
import jakarta.persistence.EntityNotFoundException;

import java.security.Principal;
import java.util.List;

public interface IPostService {
    /**
     * Retrieves a post by its ID.
     *
     * @param id The ID of the post to retrieve.
     * @return The requested post as a PostDTO.
     * @throws EntityNotFoundException if no post with the given ID can be found.
     */
    PostDTO getPost(final Long id) throws EntityNotFoundException;
    /**
     * Retrieves all posts.
     *
     * @return A list of PostDTOs representing all posts.
     */
    List<PostDTO> getPosts(Principal user);
    /**
     * Creates a new post.
     *
     * @param currentUser The current user attempting to create a post, represented as a DBUserDTO.
     * @param postDTO The post to be created, represented as a PostDTO.
     */
    PostDTO createPost(final PostDTO postDTO, final Principal user);
}
