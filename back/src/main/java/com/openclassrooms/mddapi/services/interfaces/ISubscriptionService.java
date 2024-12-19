package com.openclassrooms.mddapi.services.interfaces;

import com.openclassrooms.mddapi.dto.TopicDto;

import java.util.List;

public interface ISubscriptionService {
    public void subscribe(Long idTopic);
    public void unSubscribe(Long idTopic);
    List<TopicDto> getAllTopicsWithSubscriptionStatusForCurrentUser();
    public List<TopicDto> getAllSubscribedTopics();
}
