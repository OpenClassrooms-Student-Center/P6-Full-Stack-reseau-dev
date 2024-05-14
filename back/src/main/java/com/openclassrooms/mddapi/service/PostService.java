package com.openclassrooms.mddapi.service;

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

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@Transactional
public class PostService {

    PostRepository postRepository;

    public List<Post> findAllPosts() {
        try {
            log.info("findAllPosts");
            List<Post> postList = new ArrayList<>();
            postRepository.findAll().forEach(pt -> postList.add(pt));
            return postList;
        } catch (Exception e) {
            log.error("We could not find all posts: " + e.getMessage());
            throw new RuntimeException("We could not find any posts");
        }
    }


    public Post findPostById(Long id) {
        try {
            log.info("findPostById - id: " + id);
            Post post = postRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Post not found"));
            return post;
        } catch (Exception e) {
            log.error("We could not find post: " + id, e.getMessage());
            throw new RuntimeException("We could not find your post");
        }
    }

	public List<Post> findAllByIds(List<Long> ids){
		try {
			log.info("findAllByIds - ids: " + ids);
			List<Post> comments = postRepository.findAllById(ids);
			return comments;
		} catch (Exception e) {
			log.error("We could not find posts: " + ids, e.getMessage());
			throw new RuntimeException("We could not find your posts");
		}
	}

    public Post createPost(Post post) {
        try {
            log.info("createPost");
            post.setId(null);
            post = postRepository.save(post);
            return post;
        } catch (Exception e) {
            log.error("Failed to create post: ", e.getMessage());
            throw new RuntimeException("Failed to create post");
        }
    }

    public Post updatePost(Post post) {
        try {
            log.info("updatePost - id: " + post.getId());
            Post existingPost = postRepository.findById(post.getId())
                    .orElseThrow(() -> new RuntimeException("Post not found"));
            existingPost.setTopic(post.getTopic());
            existingPost.setArticle(post.getArticle());
            existingPost.setAuthor(post.getAuthor());
            postRepository.save(existingPost);
            return existingPost;
        } catch (Exception e) {
            log.error("Failed to update post: ", e.getMessage());
            throw new RuntimeException("Failed to update post");
        }
    }

    public String deletePost(Long id) {
        try {
            log.info("deletePost - id: " + id);
            Post post = postRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Post not found"));
            postRepository.delete(post);
            return "Post deleted";
        } catch (Exception e) {
            log.error("Failed to delete post: ", e.getMessage());
            throw new RuntimeException("Failed to delete post");
        }
    }
}