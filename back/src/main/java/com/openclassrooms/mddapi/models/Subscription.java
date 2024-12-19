package com.openclassrooms.mddapi.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@Builder
@AllArgsConstructor @NoArgsConstructor @Getter @Setter
@Entity
public class Subscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "topic_id", nullable = false)
    private Topic topic;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;



}
