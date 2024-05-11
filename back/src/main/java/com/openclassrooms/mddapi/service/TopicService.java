package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.model.Topic;
import com.openclassrooms.mddapi.repository.TopicRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Log4j2
@Transactional
public class TopicService {

    TopicRepository topicRepository;

    public List<Topic> findAllTopics() {
        try {
            log.info("findAllTopics");
            List<Topic> topicList = new ArrayList<>();
            topicRepository.findAll().forEach(tp -> topicList.add(tp));
            return topicList;
        } catch (Exception e) {
            log.error("We could not find all topics: " + e.getMessage());
            throw new RuntimeException("We could not find any topics");
        }
    }

    public Topic findTopicById(Long id) {
        try {
            log.info("findTopicById - id: " + id);
            Topic topic = topicRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Topic not found"));
            return topic;
        } catch (Exception e) {
            log.error("We could not find topic: " + id, e.getMessage());
            throw new RuntimeException("We could not find your topic");
        }
    }

    public Topic createTopic(Topic topic) {
        try {
            log.info("createTopic");
            topic.setId(null);
            topic = topicRepository.save(topic);
            return topic;
        } catch (Exception e) {
            log.error("Failed to create topic: ", e.getMessage());
            throw new RuntimeException("Failed to create topic");
        }
    }

    public Topic updateTopic(Topic topic) {
        try {
            log.info("updateTopic - id: " + topic.getId());
            Topic existingTopic = topicRepository.findById(topic.getId())
                    .orElseThrow(() -> new RuntimeException("Topic not found"));
            existingTopic.setName(topic.getName());
            topicRepository.save(existingTopic);
            return existingTopic;
        } catch (Exception e) {
            log.error("Failed to update topic: ", e.getMessage());
            throw new RuntimeException("Failed to update topic");
        }
    }

    public String deleteTopic(Long id) {
        try {
            log.info("deleteTopic - id: " + id);
            Topic topic = topicRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Topic not found"));
            topicRepository.delete(topic);
            return "Topic deleted";
        } catch (Exception e) {
            log.error("Failed to delete topic: ", e.getMessage());
            throw new RuntimeException("Failed to delete topic");
        }
    }
}