package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.exceptions.BadRequestExceptionHandler;
import com.openclassrooms.mddapi.exceptions.NotFoundExceptionHandler;
import com.openclassrooms.mddapi.model.Comment;
import com.openclassrooms.mddapi.model.Post;
import com.openclassrooms.mddapi.repository.PostRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles business logic associated with posts.
 */
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@Transactional
public class PostService {

    PostRepository postRepository;


    /**
     * Fetches all posts.
     *
     * @return list of all posts
     */
    public List<Post> findAllPosts() {
        try {
            log.info("findAllPosts");
            List<Post> postList = new ArrayList<>();
            postRepository.findAll().forEach(pt -> postList.add(pt));
            return postList;
        } catch (Exception e) {
            log.error("We could not find all posts: " + e.getMessage());
            throw new NotFoundExceptionHandler("We could not find any posts");
        }
    }

    /**
     * Fetches a post by id.
     *
     * @param id id of the post
     * @return the post with the given id
     */
    public Post findPostById(Long id) {
        try {
            log.info("findPostById - id: " + id);
            Post post = postRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Post not found"));
            return post;
        } catch (Exception e) {
            log.error("We could not find post: " + id, e.getMessage());
            throw new NotFoundExceptionHandler("We could not find your post");
        }
    }

    /**
     * Fetches posts with given ids.
     *
     * @param ids ids of the posts
     * @return the posts with the given ids
     */
    public List<Post> findAllByIds(List<Long> ids){
        try {
            log.info("findAllByIds - ids: " + ids);
            List<Post> posts = postRepository.findAllById(ids);
            return posts;
        } catch (Exception e) {
            log.error("We could not find posts: " + ids, e.getMessage());
            throw new NotFoundExceptionHandler("We could not find your posts");
        }
    }

    /**
     * Creates a new post.
     *
     * @param post the post to create
     * @return the created post with id assigned
     */
    public Post createPost(Post post) {
        try {
            log.info("createPost");
            post.setId(null);
            post.setCreatedAt(LocalDateTime.now());
            post.setUpdatedAt(LocalDateTime.now());
            post = postRepository.save(post);
            return post;
        } catch (Exception e) {
            log.error("Failed to create post: ", e.getMessage());
            throw new BadRequestExceptionHandler("Failed to create post");
        }
    }

    /**
     * Updates an existing post.
     *
     * @param post the post to update
     * @return the updated post
     */
    public Post updatePost(Post post) {
        try {
            log.info("updatePost - id: " + post.getId());
            Post existingPost = postRepository.findById(post.getId())
                    .orElseThrow(() -> new NotFoundExceptionHandler("Post not found"));
            existingPost.setTopic(post.getTopic());
            existingPost.setArticle(post.getArticle());
            existingPost.setAuthor(post.getAuthor());
            existingPost.setUpdatedAt(LocalDateTime.now());
            postRepository.save(existingPost);
            return existingPost;
        } catch (Exception e) {
            log.error("Failed to update post: ", e.getMessage());
            throw new BadRequestExceptionHandler("Failed to update post");
        }
    }

    /**
     * Deletes a post by id.
     *
     * @param id id of the post
     * @return a message indicating the post has been deleted
     */
    public String deletePost(Long id) {
        try {
            log.info("deletePost - id: " + id);
            Post post = postRepository.findById(id)
                    .orElseThrow(() -> new NotFoundExceptionHandler("Post not found"));
            postRepository.delete(post);
            return "Post deleted";
        } catch (Exception e) {
            log.error("Failed to delete post: ", e.getMessage());
            throw new BadRequestExceptionHandler("Failed to delete post");
        }
    }

    /**
     * Fetches posts based on user subscriptions.
     *
     * @param mddUserId user id for whom to fetch posts
     * @return list of posts of subscribed topics of the user
     */
    public List<Post> findPostsByUserSubscriptions (Long mddUserId){
        try {
            log.info("findAllByUserSubscriptions - userId: " + mddUserId);
            List<Post> posts = postRepository.findPostsByTopic_UsersId(mddUserId);
            return posts;
        } catch (Exception e) {
            log.error("We could not find posts with Topic subscription for user(id) : " + mddUserId, e.getMessage());
            throw new NotFoundExceptionHandler("We could not find your posts");
        }
    }
}