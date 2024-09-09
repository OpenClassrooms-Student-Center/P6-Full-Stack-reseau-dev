package com.openclassrooms.p6.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

/**
 * Entity representing a user in the system.
 */
@Entity
@Data
@Table(name = "users")
public class Users {
    /**
     * Primary key and unique identifier for the user.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * Username of the user.
     * 
     * This field is unique and serves as a means of identification for the user.
     */
    @Column(name = "username", unique = true, nullable = false, length = 50)
    private String username;

    /**
     * Email address of the user.
     *
     * This field is also unique and serves as a means of identification for the
     * user.
     */
    @Column(name = "email", unique = true, nullable = false, length = 255)
    private String email;

    /**
     * Password associated with the user's account.
     */
    @Column(name = "password", nullable = false, columnDefinition = "TEXT")
    private String password;

    /**
     * Timestamp indicating when the user account was created.
     */
    @Column(name = "createdat", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", nullable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    /**
     * Timestamp indicating the last update time of the user account.
     */
    @Column(name = "updatedat", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", nullable = false)
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}