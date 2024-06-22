package com.openclassrooms.mddapi.service.post;

import com.openclassrooms.mddapi.repository.PostRepository;

public class PostService implements IPostService {

	private PostRepository postRepository;
	
	public PostService(PostRepository postRepository) {
		this.postRepository = postRepository;
	}
	
}
