package com.openclassrooms.mddapi.service.topic;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.openclassrooms.mddapi.dto.DBUserDTO;
import com.openclassrooms.mddapi.dto.TopicDTO;
import com.openclassrooms.mddapi.model.DBUser;
import com.openclassrooms.mddapi.repository.DBUserRepository;
import org.apache.coyote.BadRequestException;
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
	public List<TopicDTO> getTopics() {
		List<Topic> topics = topicRepository.findAll();
		return topics.stream()
				.map(entity -> {
				return modelMapper.map(entity, TopicDTO.class);
				})
				.collect(Collectors.toList());
	}

	@Override
	public void subscribe(final DBUserDTO currentUser, final Long topicId) throws Exception {
		Topic topic = topicRepository.findById(topicId).orElse(null);
		DBUser user = dbUserRepository.findById(currentUser.getId()).orElse(null);
		if (topic == null || user == null) {
			throw new Exception("User or Topic not found");
		}
		user.getTopics().add(topic);
		dbUserRepository.save(user);
	}

	public void unsubscribe(final DBUserDTO currentUser, final Long topicId) throws Exception {
		Topic topic = topicRepository.findById(topicId).orElse(null);
		DBUser user = dbUserRepository.findById(currentUser.getId()).orElse(null);
		if (topic == null || user == null) {
			throw new Exception("User or Topic not found");
		}
		user.getTopics().remove(topic);
		dbUserRepository.save(user);
	}

	public Set<TopicDTO> getTopicsByUser(final DBUserDTO currentUser) throws Exception {
		DBUser user = dbUserRepository.findById(currentUser.getId()).orElse(null);
		if (user == null) {
			throw new Exception("User not found");
		}
		return user.getTopics().stream()
				.map(topic -> modelMapper.map(topic, TopicDTO.class))
				.collect(Collectors.toSet());
	}

	
}
