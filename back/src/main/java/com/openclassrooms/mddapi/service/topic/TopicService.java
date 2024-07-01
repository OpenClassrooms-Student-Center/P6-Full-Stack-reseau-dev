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

	public void subscribe(DBUserDTO currentUser, Long topicId) throws Exception {
		Topic topic = topicRepository.findById(topicId).orElse(null);
		DBUser user = dbUserRepository.findById(currentUser.getId()).orElse(null);
		if (topic == null || user == null) {
			throw new Exception("User or Topic not found");
		}

		boolean alreadyFollow = topic.getUsers().contains(user);
		if(alreadyFollow) {
			throw new BadRequestException("User already follow to this topic");
		}

		topic.getUsers().add(user);
		user.getTopics().add(topic);

		topicRepository.save(topic);
		dbUserRepository.save(user);
	}

	public void unsubscribe(DBUserDTO currentUser, Long topicId) throws Exception {
		Topic topic = topicRepository.findById(topicId).orElse(null);
		DBUser user = dbUserRepository.findById(currentUser.getId()).orElse(null);
		if (topic == null || user == null) {
			throw new Exception("User or Topic not found");
		}

		boolean alreadyUnfollow = !topic.getUsers().contains(user);
		if(alreadyUnfollow) {
			throw new BadRequestException("User already unfollow to this topic");
		}

		topic.getUsers().remove(user);
		user.getTopics().remove(topic);

		topicRepository.save(topic);
		dbUserRepository.save(user);
	}

	public Set<TopicDTO> getTopicsByUser(DBUserDTO currentUser) throws Exception {
		DBUser user = dbUserRepository.findById(currentUser.getId()).orElse(null);
		if (user == null) {
			throw new Exception("User not found");
		}
		return user.getTopics().stream()
				.map(topic -> modelMapper.map(topic, TopicDTO.class))
				.collect(Collectors.toSet());
	}

	
}
