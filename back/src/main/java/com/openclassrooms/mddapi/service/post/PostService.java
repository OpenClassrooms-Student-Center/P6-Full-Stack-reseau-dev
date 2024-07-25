package com.openclassrooms.mddapi.service.post;

import com.openclassrooms.mddapi.dto.PostDTO;
import com.openclassrooms.mddapi.dto.ResponseDTO;
import com.openclassrooms.mddapi.model.DBUser;
import com.openclassrooms.mddapi.model.Post;
import com.openclassrooms.mddapi.repository.DBUserRepository;
import com.openclassrooms.mddapi.repository.PostRepository;
import com.openclassrooms.mddapi.repository.TopicRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.openclassrooms.mddapi.util.DateUtils;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService implements IPostService {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private DateUtils DateUtils;

	private final PostRepository postRepository;

	private final TopicRepository topicRepository;

	private final DBUserRepository dbUserRepository;
	
	public PostService(PostRepository postRepository, TopicRepository topicRepository, DBUserRepository dbUserRepository) {
		this.postRepository = postRepository;
        this.topicRepository = topicRepository;
        this.dbUserRepository = dbUserRepository;
    }

	@Override
	public PostDTO getPost(final Long id) throws EntityNotFoundException {
		Post post = postRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Post not found"));
		return modelMapper.map(post, PostDTO.class);
	}
	@Override
	public List<PostDTO> getPosts() {
		return postRepository.findAll().stream()
				.map(entity -> modelMapper.map(entity, PostDTO.class))
				.collect(Collectors.toList());
	}
	@Override
	public PostDTO createPost(final PostDTO postDTO, final Principal user) {
		DBUser dbUser = dbUserRepository.findByEmail(user.getName()).orElseThrow(() -> new EntityNotFoundException("User not found"));
		Timestamp now = DateUtils.now();
		final Post post = modelMapper.map(postDTO, Post.class);
		post.setUser(dbUser);
		post.setCreatedAt(now);
		postRepository.save(post);
		return modelMapper.map(post, PostDTO.class);
	}

}
