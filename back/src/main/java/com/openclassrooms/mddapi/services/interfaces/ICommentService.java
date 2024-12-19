package com.openclassrooms.mddapi.services.interfaces;

import com.openclassrooms.mddapi.dto.CommentDto;
import com.openclassrooms.mddapi.models.Comment;

import java.util.List;

public interface ICommentService {
    public void commentToPost(CommentDto commentDto) ;
    public List<CommentDto> getAllCommentsByPostId(Long postId);
}
