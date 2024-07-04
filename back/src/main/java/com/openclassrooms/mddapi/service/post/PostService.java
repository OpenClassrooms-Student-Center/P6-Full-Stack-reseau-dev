package com.openclassrooms.mddapi.service.post;

import com.openclassrooms.mddapi.dto.DBUserDTO;
import com.openclassrooms.mddapi.dto.PostDTO;
import com.openclassrooms.mddapi.model.DBUser;
import com.openclassrooms.mddapi.model.Post;
import com.openclassrooms.mddapi.repository.PostRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.openclassrooms.mddapi.util.DateUtils;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostService implements IPostService {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private DateUtils DateUtils;

	private final PostRepository postRepository;
	
	public PostService(PostRepository postRepository) {
		this.postRepository = postRepository;
	}

	@Override
	public PostDTO getPost(final Long id) throws EntityNotFoundException {
		Optional<Post> post = postRepository.findById(id);
		if(post.isPresent()) {
			return modelMapper.map(post.get(), PostDTO.class);
		}
		else{
			throw new EntityNotFoundException("Post not found");
		}
	}
	@Override
	public List<PostDTO> getPosts() {
		List<Post> posts = postRepository.findAll();
		return posts.stream()
				.map(entity -> {
					return modelMapper.map(entity, PostDTO.class);
				})
				.collect(Collectors.toList());
	}
	@Override
	public void createPost(final DBUserDTO currentUser, final PostDTO postDTO) {
		Timestamp now = DateUtils.now();

		final Post post = modelMapper.map(postDTO, Post.class);
		DBUser user = modelMapper.map(currentUser, DBUser.class);
		post.setUserOwner(user);
		post.setCreatedAt(now);

		postRepository.save(post);
	}

}
