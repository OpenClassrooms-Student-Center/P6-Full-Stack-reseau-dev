package com.openclassrooms.mddapi.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@Entity
@Table(name = "comments")
public class Comment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "post_id", nullable = false)
	private Post postId;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private DBUser userId;

	private String content;

	@Column(name = "created_at")
	private Timestamp createdAt;

}
