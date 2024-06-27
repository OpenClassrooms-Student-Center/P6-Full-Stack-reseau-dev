package com.openclassrooms.mddapi.service.comment;

import com.openclassrooms.mddapi.dto.CommentDTO;
import com.openclassrooms.mddapi.dto.DBUserDTO;

import java.util.List;

public interface ICommentService {
    List<CommentDTO> getCommentsByPostId(Long postId);
    void createComment(DBUserDTO currentUser, CommentDTO commentDTO) throws RuntimeException;
}
