package com.openclassrooms.mddapi.service.topic;

import java.util.List;
import java.util.Set;

import com.openclassrooms.mddapi.dto.DBUserDTO;
import com.openclassrooms.mddapi.dto.TopicDTO;
import com.openclassrooms.mddapi.model.Topic;

public interface ITopicService {
	List<TopicDTO> getTopics();
	public void subscribe(DBUserDTO currentUser, Long topicId) throws Exception;
	public void unsubscribe(DBUserDTO currentUser, Long topicId) throws Exception;
	public Set<TopicDTO> getTopicsByUser(DBUserDTO currentUser) throws Exception;
}
