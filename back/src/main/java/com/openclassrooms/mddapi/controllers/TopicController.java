package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.dto.TopicDto;
import com.openclassrooms.mddapi.mapper.TopicMapper;
import com.openclassrooms.mddapi.models.Topic;
import com.openclassrooms.mddapi.services.impl.TopicService;
import com.openclassrooms.mddapi.services.interfaces.ISubscriptionService;
import com.openclassrooms.mddapi.services.interfaces.ITopicService;
import com.openclassrooms.mddapi.services.interfaces.IUserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/topic")
@RequiredArgsConstructor
public class TopicController {

    private final ITopicService topicService;
    private final TopicMapper topicMapper;
    private final ISubscriptionService subscriptionService;

    @PostMapping
    public ResponseEntity<?> createTopic(@RequestBody final TopicDto topicDto) {
        return ResponseEntity.ok().body(topicService.create(topicDto));

    }

    @GetMapping
    public ResponseEntity<?> getAllTopics() {
        List<TopicDto> topicDtoList = subscriptionService.getAllTopicsWithSubscriptionStatusForCurrentUser();
        return ResponseEntity.ok().body(topicDtoList);
    }




}
