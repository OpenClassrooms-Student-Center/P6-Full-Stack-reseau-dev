package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.dtos.PostDto;
import com.openclassrooms.mddapi.dtos.requests.NewPostRequest;
import com.openclassrooms.mddapi.dtos.responses.MessageResponse;
import com.openclassrooms.mddapi.dtos.responses.PostToDisplayResponse;
import com.openclassrooms.mddapi.mappers.PostMapper;
import com.openclassrooms.mddapi.model.MddUser;
import com.openclassrooms.mddapi.model.Post;
import com.openclassrooms.mddapi.service.MddUserService;
import com.openclassrooms.mddapi.service.PostService;
import com.openclassrooms.mddapi.service.TopicService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    @Autowired
    private MddUserService mddUserService;

    @Autowired
    private TopicService topicService;

    @GetMapping("/posts")
    public ResponseEntity<List<PostDto>> getPosts() {
        return ResponseEntity.ok(postMapper.toDto(postService.findAllPosts()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(postMapper.toDto(postService.findPostById(id)));
    }

    @PostMapping("/create")
    public ResponseEntity<PostDto> create(@RequestBody PostDto PostDto) {
        return ResponseEntity.ok(postMapper.toDto(postService.createPost(postMapper.toEntity(PostDto))));
    }

    @PutMapping("/update")
    public ResponseEntity<PostDto> update(@RequestBody PostDto PostDto) {
        return ResponseEntity.ok(postMapper.toDto(postService.updatePost(postMapper.toEntity(PostDto))));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> delete(@PathVariable("id") Long id) {
        return ResponseEntity.ok(new MessageResponse(postService.deletePost(id)));
    }

    @PostMapping("/newpost")
    public ResponseEntity<MessageResponse> newPost(@RequestBody NewPostRequest newPostRequest, Authentication authentication){
        Post post = Post.builder().build().setArticle(newPostRequest.getContent()).setAuthor(mddUserService.findUserByUsername(authentication.getName()))
                        .setTitle(newPostRequest.getTitle()).setTopic(topicService.findTopicById(newPostRequest.getTopicId()));
        postService.createPost(post);
        return ResponseEntity.ok(new MessageResponse());
    }

    @GetMapping("allposts")
    public ResponseEntity<List<PostToDisplayResponse>> allposts(){
        List<Post> posts = postService.findAllPosts();
        List<PostToDisplayResponse> postResponses = posts.stream()
                .map(post -> PostToDisplayResponse.builder()
                        .id(post.getId())
                        .title(post.getTitle())
                        .article(post.getArticle())
                        .authorName(post.getAuthor().getUsername())
                        .topicName(post.getTopic().getName())
                        .createdAt(post.getCreatedAt())
                        .updatedAt(post.getUpdatedAt())
                        .build())
                .collect(Collectors.toList());
        return ResponseEntity.ok(postResponses);
    }
}