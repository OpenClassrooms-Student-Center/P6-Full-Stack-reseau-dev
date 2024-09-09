package com.openclassrooms.p6.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * Entity representing a comment in the system.
 */
@Entity
@Data
@Table(name = "comments")
public class Comments {
    /**
     * Primary key and unique identifier for the comment.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * Author who created the article. This field won't be added to the database.
     */
    @ManyToOne
    @JoinColumn(name = "userid", nullable = false, insertable = false, updatable = false)
    private Users user;

    /**
     * User ID of the author of the article
     */
    @Column(name = "userid")
    private Long userId;

    /**
     * Article the user's writing the comment to. This field won't be added to the
     * database.
     */
    @ManyToOne
    @JoinColumn(name = "articleid", nullable = false, insertable = false, updatable = false)
    private Articles article;

    /**
     * ID of the article
     */
    @Column(name = "articleid")
    private Long articleId;

    /**
     * Comment of the article
     */
    @Column(name = "comment", columnDefinition = "TEXT")
    private String comment;

    /**
     * Timestamp indicating when the comment was created.
     */
    @Column(name = "createdat", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", nullable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    /**
     * Timestamp indicating the last update time of the comment.
     */
    @Column(name = "updatedat", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", nullable = false)
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
