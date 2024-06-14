package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.dtos.PostDto;
import com.openclassrooms.mddapi.dtos.requests.NewPostRequest;
import com.openclassrooms.mddapi.dtos.responses.MessageResponse;
import com.openclassrooms.mddapi.dtos.responses.PostToDisplayResponse;
import com.openclassrooms.mddapi.mappers.PostMapper;
import com.openclassrooms.mddapi.mappers.ResponsesMapper;
import com.openclassrooms.mddapi.model.MddUser;
import com.openclassrooms.mddapi.model.Post;
import com.openclassrooms.mddapi.service.MddUserService;
import com.openclassrooms.mddapi.service.PostService;
import com.openclassrooms.mddapi.service.TopicService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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

/**
 * This is the controller which manages requests for posts
 */
@RestController
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@RequestMapping("posts/post")
@SecurityRequirement(name = "Authorization")
public class PostController {

    @Autowired
    PostService postService;

    @Autowired
    private PostMapper postMapper;

    @Autowired
    private MddUserService mddUserService;

    @Autowired
    private TopicService topicService;

    @Autowired
    private ResponsesMapper responsesMapper;

    /**
    * Retrieve all existing posts
    * @return posts wrapped in HTTP response
    */
    @GetMapping("/posts")
    public ResponseEntity<List<PostDto>> getPosts() {
        return ResponseEntity.ok(postMapper.toDto(postService.findAllPosts()));
    }

    /**
    * Retrieve a specific post by ID
    * @param id the id of the post to retrieve
    * @return the post wrapped in HTTP response
    */
    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(postMapper.toDto(postService.findPostById(id)));
    }

    /**
    * Create a new post
    * @param PostDto the post to create
    * @return the created post wrapped in HTTP response
    */
    @PostMapping("/create")
    public ResponseEntity<PostDto> create(@RequestBody PostDto PostDto) {
        return ResponseEntity.ok(postMapper.toDto(postService.createPost(postMapper.toEntity(PostDto))));
    }

    /**
    * Update an existing post
    * @param PostDto the updated post
    * @return the updated post wrapped in HTTP response
    */
    @PutMapping("/update")
    public ResponseEntity<PostDto> update(@RequestBody PostDto PostDto) {
        return ResponseEntity.ok(postMapper.toDto(postService.updatePost(postMapper.toEntity(PostDto))));
    }
    /**
    * Delete a post
    * @param id the id of the post to delete
    * @return a message response wrapped in HTTP response
    */
    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> delete(@PathVariable("id") Long id) {
        return ResponseEntity.ok(new MessageResponse(postService.deletePost(id)));
    }

    /**
    * Creates a new post
    * @param newPostRequest specifications of the new post
    * @param authentication the authentication object
    * @return a message response wrapped in HTTP response
    */
    @PostMapping("/newpost")
    public ResponseEntity<MessageResponse> newPost(@RequestBody NewPostRequest newPostRequest, Authentication authentication){
        Post post = Post.builder().build().setArticle(newPostRequest.getContent()).setAuthor(mddUserService.findUserByEmail(authentication.getName()))
                        .setTitle(newPostRequest.getTitle()).setTopic(topicService.findTopicById(newPostRequest.getTopicId()));
        postService.createPost(post);
        return ResponseEntity.ok(new MessageResponse());
    }

    /**
    * Retrieve all posts
    * @return the list of all posts wrapped in HTTP response
    */
    @GetMapping("allposts")
    public ResponseEntity<List<PostToDisplayResponse>> allposts(){
        List<Post> posts = postService.findAllPosts();
        return ResponseEntity.ok(responsesMapper.postsToPostDisplayResponses(posts));
    }

    /**
    * Retrieve posts based on user's subscription
    * @param authentication the authentication object
    * @return the list of posts wrapped in HTTP response
    */
    @GetMapping("/subscribedtopicposts")
    public ResponseEntity<List<PostToDisplayResponse>> getPostsBySubscription(Authentication authentication) {
        MddUser user = mddUserService.findUserByEmail(authentication.getName());
        return ResponseEntity.ok(responsesMapper.postsToPostDisplayResponses(postService.findPostsByUserSubscriptions(user.getId())));
    }
}