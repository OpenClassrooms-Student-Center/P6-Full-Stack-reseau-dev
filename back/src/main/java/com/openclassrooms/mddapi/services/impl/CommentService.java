package com.openclassrooms.mddapi.services.impl;

import com.openclassrooms.mddapi.dto.CommentDto;
import com.openclassrooms.mddapi.dto.PostDto;
import com.openclassrooms.mddapi.mapper.CommentMapper;
import com.openclassrooms.mddapi.mapper.PostMapper;
import com.openclassrooms.mddapi.models.Post;
import com.openclassrooms.mddapi.models.Comment;
import com.openclassrooms.mddapi.repositories.CommentRepository;
import com.openclassrooms.mddapi.services.interfaces.IPostService;
import com.openclassrooms.mddapi.services.interfaces.ICommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class CommentService implements ICommentService {
    private final CommentRepository commentRepository;
    private final IPostService postService;
    private final CommentMapper commentMapper;
    private final UserService userService;



    @Override
    public void commentToPost(CommentDto commentDto) {
        Post post = postService.findById(commentDto.getPostId());
        Comment commentToBeSaved = commentMapper.toEntity(commentDto);
        commentToBeSaved.setAuthor(userService.getConnectedUser());
        commentToBeSaved.setPost(post);
        commentRepository.save(commentToBeSaved);

    }

    @Override
    public List<CommentDto> getAllCommentsByPostId(Long postId) {
        return commentRepository.findByPostId(postId)
                .stream()
                .map(comment -> commentMapper.toDto(comment))
                .collect(Collectors.toList());
    }


}
