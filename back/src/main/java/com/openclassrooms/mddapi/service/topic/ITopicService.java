package com.openclassrooms.mddapi.service.topic;

import java.util.List;

import com.openclassrooms.mddapi.dto.DBUserDTO;
import com.openclassrooms.mddapi.dto.TopicDTO;
import com.openclassrooms.mddapi.model.DBUser;
import org.apache.coyote.BadRequestException;

public interface ITopicService {
	List<TopicDTO> getTopics();
	public void follow(DBUserDTO currentUser, Long topicId) throws Exception;
	public void unfollow(DBUserDTO currentUser, Long topicId) throws Exception;
}
