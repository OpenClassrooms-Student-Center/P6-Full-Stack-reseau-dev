package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.dtos.CommentDto;
import com.openclassrooms.mddapi.mappers.CommentMapper;
import com.openclassrooms.mddapi.service.CommentService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@RequestMapping("users/user")
public class CommentController {

    @Autowired
    CommentService commentService;

    @Autowired
    private CommentMapper commentMapper;

    @GetMapping("/comments")
    public ResponseEntity<List<CommentDto>> getUsers() {
        return ResponseEntity.ok(commentMapper.toDto(commentService.findAllComments()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(commentMapper.toDto(commentService.findCommentById(id)));
    }

    @PutMapping("/create")
    public ResponseEntity<CommentDto> create(@RequestBody CommentDto commentDto) {
        return ResponseEntity.ok(commentMapper.toDto(commentService.createComment(commentMapper.toEntity(commentDto))));
    }

    @PostMapping("update")
    public ResponseEntity<CommentDto> update(@RequestBody CommentDto commentDto) {
        return ResponseEntity.ok(commentMapper.toDto(commentService.updateComment(commentMapper.toEntity(commentDto))));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id) {
        return ResponseEntity.ok(commentService.deleteComment(id));
    }
}