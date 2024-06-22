package com.openclassrooms.mddapi.model;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.sql.Timestamp;

@Entity
@Getter
@Setter
@Table(name = "posts")
public class Post {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "topic_id", nullable = false)
	private Topic topicId;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private DBUser userId;

	@Column(name="title", nullable = false)
	private String title;

	@Column(name="content")
	private String content;

	@Column(name="created_at")
	private Timestamp createdAt;
}
