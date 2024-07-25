package com.openclassrooms.mddapi.service.comment;

import com.openclassrooms.mddapi.dto.CommentDTO;
import com.openclassrooms.mddapi.dto.PostDTO;
import com.openclassrooms.mddapi.dto.ResponseDTO;

import java.security.Principal;
import java.util.List;

public interface ICommentService {
    /**
     * Retrieves all comments for a given post.
     *
     * @param postId The ID of the post for which comments are to be retrieved.
     * @return A list of CommentDTOs representing all comments for the specified post.
     */
    List<CommentDTO> getCommentsByPost(Long postId);
    /**
     * Creates a new comment.
     *
     * @param currentUser The current user attempting to create a comment, represented as a DBUserDTO.
     * @param commentDTO The comment to be created, represented as a CommentDTO.
     */
    List<CommentDTO> createComment(CommentDTO commentDTO, Long postId, Principal user);
}
