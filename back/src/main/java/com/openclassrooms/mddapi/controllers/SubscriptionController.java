package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.dto.TopicDto;
import com.openclassrooms.mddapi.services.interfaces.ISubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/subscription")
@RequiredArgsConstructor
public class SubscriptionController {


    private final ISubscriptionService subscriptionService;


    @PostMapping("/{idTopic}")
    public ResponseEntity<?> subscribeUserToTopic(@PathVariable final Long idTopic) {
        subscriptionService.subscribe(idTopic);

        return ResponseEntity.ok().build();

    }
    @DeleteMapping("{idTopic}")
    public ResponseEntity<?> unsubscribeUserFromTopic(@PathVariable final Long idTopic) {
        subscriptionService.unSubscribe(idTopic);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/topics")
    public ResponseEntity<?> getAllTopics() {
        List<TopicDto> topicDtoList = subscriptionService.getAllTopicsWithSubscriptionStatusForCurrentUser();
        return ResponseEntity.ok().body(topicDtoList);
    }

    @GetMapping
    public ResponseEntity<?> getAllSubscribedTopics() {
        List<TopicDto> topicDtoList = subscriptionService.getAllSubscribedTopics();
        return ResponseEntity.ok().body(topicDtoList);
    }

}
