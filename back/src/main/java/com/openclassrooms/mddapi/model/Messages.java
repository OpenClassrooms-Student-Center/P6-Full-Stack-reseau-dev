package com.openclassrooms.mddapi.model;
import javax.persistence.*;
import org.springframework.lang.Nullable;
import lombok.Data;
import java.time.LocalDateTime;
@Data
@Entity
@Table(name = "Messages")
public class Messages {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "Article_id")
    @Nullable 
    private Article rental;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "users_id")
    private User user;
    private String message;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    
}