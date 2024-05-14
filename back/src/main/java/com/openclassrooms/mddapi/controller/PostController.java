package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.dtos.PostDto;
import com.openclassrooms.mddapi.mappers.PostMapper;
import com.openclassrooms.mddapi.service.PostService;
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
@RequestMapping("posts/post")
public class PostController {

    @Autowired
    PostService postService;

    @Autowired
    private PostMapper postMapper;

    @GetMapping("/posts")
    public ResponseEntity<List<PostDto>> getPosts() {
        return ResponseEntity.ok(postMapper.toDto(postService.findAllPosts()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(postMapper.toDto(postService.findPostById(id)));
    }

    @PutMapping("/create")
    public ResponseEntity<PostDto> create(@RequestBody PostDto PostDto) {
        return ResponseEntity.ok(postMapper.toDto(postService.createPost(postMapper.toEntity(PostDto))));
    }

    @PostMapping("/update")
    public ResponseEntity<PostDto> update(@RequestBody PostDto PostDto) {
        return ResponseEntity.ok(postMapper.toDto(postService.updatePost(postMapper.toEntity(PostDto))));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id) {
        return ResponseEntity.ok(postService.deletePost(id));
    }
}