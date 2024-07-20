package com.openclassrooms.mddapi.service.topic;

import java.security.Principal;
import java.util.stream.Collectors;

import com.openclassrooms.mddapi.dto.*;
import com.openclassrooms.mddapi.model.DBUser;
import com.openclassrooms.mddapi.repository.DBUserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.mddapi.model.Topic;
import com.openclassrooms.mddapi.repository.TopicRepository;

@Service
public class TopicService implements ITopicService {

	@Autowired
	private ModelMapper modelMapper;

	private final TopicRepository topicRepository;
	private final DBUserRepository dbUserRepository;

	public TopicService(TopicRepository topicRepository, DBUserRepository dbUserRepository) {

		this.topicRepository = topicRepository;
        this.dbUserRepository = dbUserRepository;
    }

	@Override
	public TopicsDTO getTopics() {
		return TopicsDTO.builder().topics(topicRepository.findAll().stream()
			.map(entity -> {
				return modelMapper.map(entity, TopicDTO.class);
			})
			.collect(Collectors.toList())).build();
	}

	@Override
	public ResponseDTO subscribe(final Principal currentUser, final Long topicId) throws Exception {
		Topic topic = topicRepository.findById(topicId).orElseThrow(() -> new EntityNotFoundException("Topic not found"));
		DBUser user = dbUserRepository.findByEmail(currentUser.getName()).orElseThrow(() -> new EntityNotFoundException("User not found"));
		user.getTopics().add(topic);
		dbUserRepository.save(user);
		return new ResponseDTO("Topic followed !");
	}

	public ResponseDTO unsubscribe(final Principal currentUser, final Long topicId) throws Exception {
		Topic topic = topicRepository.findById(topicId).orElseThrow(() -> new EntityNotFoundException("Topic not found"));
		DBUser user = dbUserRepository.findByEmail(currentUser.getName()).orElseThrow(() -> new EntityNotFoundException("User not found"));
		user.getTopics().remove(topic);
		dbUserRepository.save(user);
		return new ResponseDTO("Topic unfollowed !");
	}

	public TopicsDTO getTopicsByUser(final Principal currentUser) throws Exception {
		DBUser user = dbUserRepository.findByEmail(currentUser.getName()).orElseThrow(() -> new EntityNotFoundException("User not found"));
		return TopicsDTO.builder().topics(user.getTopics().stream()
			.map(entity -> {
				return modelMapper.map(entity, TopicDTO.class);
			})
			.collect(Collectors.toList())).build();
	}

	
}
