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
 * Entity representing an article in the system.
 */
@Entity
@Data
@Table(name = "articles")
public class Articles {
    /**
     * Primary key and unique identifier for the article.
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
     * Theme of the article. This field won't be added to the database.
     */
    @ManyToOne
    @JoinColumn(name = "themeid", nullable = false, insertable = false, updatable = false)
    private Themes theme;

    /**
     * Theme ID of the article.
     */
    @Column(name = "themeid")
    private Long themeId;

    @Column(name = "title", nullable = false, length = 255)
    private String title;

    /**
     * Description of the article
     */
    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    private String description;

    /**
     * Timestamp indicating when the article was created.
     */
    @Column(name = "createdat", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", nullable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    /**
     * Timestamp indicating the last update time of the article.
     */
    @Column(name = "updatedat", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", nullable = false)
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
