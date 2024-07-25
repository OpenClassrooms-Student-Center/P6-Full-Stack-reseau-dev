package com.openclassrooms.mddapi.service.topic;

import java.security.Principal;
import java.util.List;
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
	public List<TopicDTO> findAll() {
		return topicRepository.findAll().stream()
				.map(entity -> modelMapper.map(entity, TopicDTO.class))
				.collect(Collectors.toList());
	}

	@Override
	public List<TopicDTO> getTopicsNotFollowedByUser(final Principal user) {
		List<TopicDTO> userTopics = this.getTopicsByUser(user);
		List<TopicDTO> allTopics = topicRepository.findAll().stream()
				.map(entity -> modelMapper.map(entity, TopicDTO.class))
				.collect(Collectors.toList());
		return allTopics.stream()
				.filter(topic -> userTopics.stream().noneMatch(userTopic -> userTopic.getId().equals(topic.getId())))
				.collect(Collectors.toList());
	}

	@Override
	public List<TopicDTO> subscribe(final Principal currentUser, final Long topicId) {
		Topic topic = topicRepository.findById(topicId).orElseThrow(() -> new EntityNotFoundException("Topic not found"));
		DBUser user = dbUserRepository.findByEmail(currentUser.getName()).orElseThrow(() -> new EntityNotFoundException("User not found"));
		user.getTopics().add(topic);
		dbUserRepository.save(user);
		return this.getTopicsNotFollowedByUser(currentUser);
	}

	public List<TopicDTO> unsubscribe(final Principal currentUser, final Long topicId) {
		Topic topic = topicRepository.findById(topicId).orElseThrow(() -> new EntityNotFoundException("Topic not found"));
		DBUser user = dbUserRepository.findByEmail(currentUser.getName()).orElseThrow(() -> new EntityNotFoundException("User not found"));
		user.getTopics().remove(topic);
		dbUserRepository.save(user);
		return this.getTopicsByUser(currentUser);
	}

	public List<TopicDTO> getTopicsByUser(final Principal currentUser) {
		DBUser user = dbUserRepository.findByEmail(currentUser.getName()).orElseThrow(() -> new EntityNotFoundException("User not found"));
		return user.getTopics().stream()
				.map(entity -> modelMapper.map(entity, TopicDTO.class))
				.collect(Collectors.toList());
	}

	
}
