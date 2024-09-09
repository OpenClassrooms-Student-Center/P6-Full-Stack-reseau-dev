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
 * Entity representing a subscription in the system.
 */
@Entity
@Data
@Table(name = "subscriptions")
public class Subscriptions {
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

    /**
     * Boolean to indicate whether the user is subscribed to the theme or not.
     */
    @Column(name = "issubscribed")
    private Boolean isSubscribed;

    /**
     * Timestamp indicating when the subscription was created.
     */
    @Column(name = "createdat", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", nullable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    /**
     * Timestamp indicating the last update time of the subscription.
     */
    @Column(name = "updatedat", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", nullable = false)
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
