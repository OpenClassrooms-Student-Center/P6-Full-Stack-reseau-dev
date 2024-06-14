package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.exceptions.BadRequestExceptionHandler;
import com.openclassrooms.mddapi.exceptions.NotFoundExceptionHandler;
import com.openclassrooms.mddapi.model.Comment;
import com.openclassrooms.mddapi.model.MddUser;
import com.openclassrooms.mddapi.model.Post;
import com.openclassrooms.mddapi.repository.CommentRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * CommentService is a service for handling comments operations such as create, read, update, delete.
 *
 */
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@Transactional
public class CommentService {

    CommentRepository commentRepository;
    /**
     * Get all the comments from database.
     *
     * @return the list of comments.
     * @throws NotFoundExceptionHandler when we could not find any comments.
     */
    public List<Comment> findAllComments() {
        try {
            log.info("findAllComments");
            List<Comment> commentList = new ArrayList<>();
            commentRepository.findAll().forEach(ct -> commentList.add(ct));
            return commentList;
        } catch (Exception e) {
            log.error("We could not find all comments: " + e.getMessage());
            throw new NotFoundExceptionHandler("We could not find any comments");
        }
    }

    /**
     * Get a specific comment by id from database.
     *
     * @param id the id of comment.
     * @return the comment.
     * @throws NotFoundExceptionHandler when we could not find the comment.
     */
    public Comment findCommentById(Long id) {
        try {
            log.info("findCommentById - id: " + id);
            Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new NotFoundExceptionHandler("Comment not found"));
            return comment;
        } catch (Exception e) {
            log.error("We could not find comment: " + id, e.getMessage());
            throw new NotFoundExceptionHandler("We could not find your comment");
        }
    }

    /**
     * Get a list of comments by a list of ids from database.
     *
     * @param ids the ids of comments.
     * @return a list of comments.
     * @throws NotFoundExceptionHandler when we could not find the comments.
     */
    public List<Comment> findAllByIds(List<Long> ids){
        try {
            log.info("findAllByIds - ids: " + ids);
            List<Comment> comments = commentRepository.findAllById(ids);
            return comments;
        } catch (Exception e) {
            log.error("We could not find comments: " + ids, e.getMessage());
            throw new NotFoundExceptionHandler("We could not find your comments");
        }
    }

    /**
     * Create a comment.
     *
     * @param comment the comment to create
     * @return the newly created comment.
     * @throws BadRequestExceptionHandler when we failed to create comment.
     */
    public Comment createComment(Comment comment) {
        try {
            log.info("createComment");
            comment.setId(null);
            comment = commentRepository.save(comment);
            return comment;
        } catch (Exception e) {
            log.error("Failed to create comment: ", e.getMessage());
            throw new BadRequestExceptionHandler("Failed to create comment");
        }
    }

    /**
     * Update a specific comment.
     *
     * @param comment the updated comment.
     * @return the updated comment.
     * @throws BadRequestExceptionHandler when we failed to update comment.
     */
    public Comment updateComment(Comment comment) {
        try {
            log.info("updateComment - id: " + comment.getId());
            Comment existingComment = commentRepository.findById(comment.getId())
                    .orElseThrow(() -> new NotFoundExceptionHandler("Comment not found"));
            existingComment.setPost(comment.getPost());
            existingComment.setAuthor(comment.getAuthor());
            commentRepository.save(existingComment);
            return existingComment;
        } catch (Exception e) {
            log.error("Failed to update comment: ", e.getMessage());
            throw new BadRequestExceptionHandler("Failed to update comment");
        }
    }

    /**
     * Delete a specific comment by id.
     *
     * @param id of the comment.
     * @return a string indicate that the specified comment was deleted.
     * @throws BadRequestExceptionHandler when we failed to delete comment.
     */
    public String deleteComment(Long id) {
        try {
            log.info("deleteComment - id: " + id);
            Comment comment = commentRepository.findById(id)
                    .orElseThrow(() -> new NotFoundExceptionHandler("Comment not found"));
            commentRepository.delete(comment);
            return "Comment deleted";
        } catch (Exception e) {
            log.error("Failed to delete comment: ", e.getMessage());
            throw new BadRequestExceptionHandler("Failed to delete comment");
        }
    }

    /**
     * Find all the comments by post id.
     *
     * @param postId the id of the post.
     * @return a list of comments for this post.
     * @throws NotFoundExceptionHandler when we could not find comments for the post.
     */
    public List<Comment> findCommentsByPostId(Long postId) {
        try {
            log.info("findCommentsByPostId - postId: " + postId);
            List<Comment> comments = commentRepository.findCommentsByPostId(postId);
            return comments;
        } catch (Exception e) {
            log.error("We could not find comments for post: " + postId, e.getMessage());
            throw new NotFoundExceptionHandler("We could not find comments for the post");
        }
    }
}