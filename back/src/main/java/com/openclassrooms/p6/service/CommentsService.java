package com.openclassrooms.p6.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.p6.model.Comments;
import com.openclassrooms.p6.payload.request.CommentRequest;
import com.openclassrooms.p6.repository.CommentRepository;

import lombok.Data;

/**
 * Service class for managing comments.
 */
@Data
@Service
public class CommentsService {

    /**
     * 
     * Comments repo to perform database operations on the {@link Comments} entity.
     */
    @Autowired
    private CommentRepository commentRepository;

    /**
     * Retrieve all comments.
     *
     * @return A collection of all comments as a list.
     */
    public List<Comments> getComments() {
        return commentRepository.findAll();
    }

    /**
     * Retrieves all the comments related to an article by their ID.
     *
     * @param id The identifier of the article.
     * @return A list of all the comments related to the article
     */
    public List<Comments> getAllCommentsByArticleId(final Long id) {
        return commentRepository.findAllByArticleId(id);
    }

    /**
     * Creates a comment for an article.
     *
     * @param userId   User ID of the creator of the comment.
     * @param comments The comments to be created for an article.
     * @return The saved or updated comments.
     */
    public Comments createComment(CommentRequest commentRequest, Long userId, Long articleId) {
        Comments newComments = new Comments();

        newComments.setArticleId(articleId);
        newComments.setUserId(userId);
        newComments.setComment(commentRequest.comment());

        return commentRepository.save(newComments);
    }
}
