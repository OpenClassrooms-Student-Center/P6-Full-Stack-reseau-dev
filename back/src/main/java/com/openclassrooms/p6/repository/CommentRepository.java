package com.openclassrooms.p6.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.openclassrooms.p6.model.Comments;

/**
 * Repository interface for managing Comment entities in the database.
 */
@Repository
public interface CommentRepository extends JpaRepository<Comments, Long> {
    /**
     * Retrieves all comments associated with a specific article ID.
     *
     * @param articleId The identifier of the article.
     * @return A list of comments associated with the specified article ID.
     */
    List<Comments> findAllByArticleId(Long articleId);
}
