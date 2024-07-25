package com.openclassrooms.mddapi.model;

import com.openclassrooms.mddapi.dto.CommentDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "posts")
public class Post {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "topic_id", nullable = false)
	private Topic topic;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id", nullable = false)
	private DBUser userOwner;

	@Column(name="title", nullable = false)
	private String title;

	@Column(name="content")
	private String content;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "post")
	private List<Comment> comments;

	@Column(name="created_at")
	private Timestamp createdAt;

	@Column(name = "updated_at")
	private Timestamp updatedAt;
}
