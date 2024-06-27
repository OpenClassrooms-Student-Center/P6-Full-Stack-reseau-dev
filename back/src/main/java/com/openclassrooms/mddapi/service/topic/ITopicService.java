package com.openclassrooms.mddapi.service.topic;

import java.util.List;

import com.openclassrooms.mddapi.dto.DBUserDTO;
import com.openclassrooms.mddapi.dto.TopicDTO;

public interface ITopicService {
	List<TopicDTO> getTopics();
	public void follow(DBUserDTO currentUser, Long topicId) throws Exception;
	public void unfollow(DBUserDTO currentUser, Long topicId) throws Exception;
}
