package com.openclassrooms.mddapi.service.post;

import com.openclassrooms.mddapi.dto.PostDTO;
import com.openclassrooms.mddapi.dto.ResponseDTO;
import com.openclassrooms.mddapi.dto.TopicDTO;
import com.openclassrooms.mddapi.model.DBUser;
import com.openclassrooms.mddapi.model.Post;
import com.openclassrooms.mddapi.repository.DBUserRepository;
import com.openclassrooms.mddapi.repository.PostRepository;
import com.openclassrooms.mddapi.repository.TopicRepository;
import com.openclassrooms.mddapi.service.topic.ITopicService;
import com.openclassrooms.mddapi.service.topic.TopicService;
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

	private final ITopicService topicService;

	private final DBUserRepository dbUserRepository;
	
	public PostService(PostRepository postRepository, ITopicService topicService, DBUserRepository dbUserRepository) {
		this.postRepository = postRepository;
        this.topicService = topicService;
        this.dbUserRepository = dbUserRepository;
    }

	@Override
	public PostDTO getPost(final Long id) throws EntityNotFoundException {
		Post post = postRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Post not found"));
		return modelMapper.map(post, PostDTO.class);
	}
	@Override
	public List<PostDTO> getPosts(Principal user) {
		List<TopicDTO> topics = topicService.getTopicsByUser(user);
		List<Post> posts = postRepository.findAll();
		return posts.stream()
				.filter(post -> topics.stream().anyMatch(topic -> topic.getId().equals(post.getTopic().getId())))
				.map(post -> modelMapper.map(post, PostDTO.class))
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
