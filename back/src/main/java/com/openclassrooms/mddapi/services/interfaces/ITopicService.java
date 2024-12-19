package com.openclassrooms.mddapi.services.interfaces;

import com.openclassrooms.mddapi.dto.PostDto;
import com.openclassrooms.mddapi.dto.TopicDto;
import com.openclassrooms.mddapi.models.Topic;

import java.util.List;

public interface ITopicService {
    public TopicDto create(TopicDto topic);
    public List<TopicDto> findAll();
    public Topic findById(Long id);
    public Topic findBySubject(String subject);
}
