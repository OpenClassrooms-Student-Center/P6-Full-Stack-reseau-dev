package com.openclassrooms.mddapi.model;

import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Entity
@Data
@Table(name = "Article")
public class Article {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	private String title;
	
	@Size(max = 5000)
    @NotNull
	private String description;
	 

	@ManyToMany(fetch = FetchType.EAGER ,cascade = CascadeType.ALL)
	@JoinTable(name = "article_Messages", joinColumns = @JoinColumn(name = "Article_id"), inverseJoinColumns = @JoinColumn(name = "Messages_id"))
	private Set<Messages> messages;

	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    private User author;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "theme_id", referencedColumnName = "id")
	private Themes theme;

	@Column(name = "created_at")
	private LocalDateTime createdAt;

	@Column(name = "updated_at")
	private LocalDateTime updatedAt;
	

	
	
	
}
