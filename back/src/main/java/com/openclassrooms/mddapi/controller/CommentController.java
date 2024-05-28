package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.dtos.CommentDto;
import com.openclassrooms.mddapi.dtos.responses.CommentToDisplayResponse;
import com.openclassrooms.mddapi.dtos.responses.MessageResponse;
import com.openclassrooms.mddapi.mappers.CommentMapper;
import com.openclassrooms.mddapi.service.CommentService;
import com.openclassrooms.mddapi.service.MddUserService;
import com.openclassrooms.mddapi.service.PostService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@RequestMapping("comments/comment")
public class CommentController {

    @Autowired
    CommentService commentService;

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    PostService postService;

    @Autowired
    MddUserService mddUserService;

    @GetMapping("/comments")
    public ResponseEntity<List<CommentDto>> getUsers() {
        return ResponseEntity.ok(commentMapper.toDto(commentService.findAllComments()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(commentMapper.toDto(commentService.findCommentById(id)));
    }

    @PostMapping("/create")
    public ResponseEntity<CommentDto> create(@RequestBody CommentDto commentDto) {
        return ResponseEntity.ok(commentMapper.toDto(commentService.createComment(commentMapper.toEntity(commentDto))));
    }

    @PutMapping("/update")
    public ResponseEntity<CommentDto> update(@RequestBody CommentDto commentDto) {
        return ResponseEntity.ok(commentMapper.toDto(commentService.updateComment(commentMapper.toEntity(commentDto))));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> delete(@PathVariable("id") Long id) {
        return ResponseEntity.ok(new MessageResponse(commentService.deleteComment(id)));
    }

    @GetMapping("/bypost/{id}")
    public ResponseEntity<List<CommentToDisplayResponse>> commentByPost(@PathVariable("id") Long id){
        return ResponseEntity.ok(postService.findPostById(id).getComments().stream().map(comment ->
                CommentToDisplayResponse.builder()
                        .text(comment.getText())
                        .authorName(comment.getAuthor().getUsername())
                        .id(comment.getId())
                        .updatedAt(comment.getUpdatedAt())
                        .createdAt(comment.getCreatedAt())
                        .build()).collect(Collectors.toList())
        );
    }
}