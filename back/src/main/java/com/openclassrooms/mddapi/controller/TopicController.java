package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.dtos.TopicDto;
import com.openclassrooms.mddapi.dtos.responses.MessageResponse;
import com.openclassrooms.mddapi.mappers.TopicMapper;
import com.openclassrooms.mddapi.model.MddUser;
import com.openclassrooms.mddapi.service.MddUserService;
import com.openclassrooms.mddapi.service.TopicService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/topic")
public class TopicController {

    @Autowired
    TopicService topicService;

    @Autowired
    private TopicMapper topicMapper;

    @Autowired
    private MddUserService mddUserService;

    @GetMapping("/topics")
    public ResponseEntity<List<TopicDto>> getTopics() {
        return ResponseEntity.ok(topicMapper.toDto(topicService.findAllTopics()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TopicDto> getTopicById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(topicMapper.toDto(topicService.findTopicById(id)));
    }

    @PostMapping("/create")
    public ResponseEntity<TopicDto> create(@RequestBody TopicDto topicDto) {
        return ResponseEntity.ok(topicMapper.toDto(topicService.createTopic(topicMapper.toEntity(topicDto))));
    }

    @PutMapping("/update")
    public ResponseEntity<TopicDto> update(@RequestBody TopicDto topicDto) {
        return ResponseEntity.ok(topicMapper.toDto(topicService.updateTopic(topicMapper.toEntity(topicDto))));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> delete(@PathVariable("id") Long id) {
        return ResponseEntity.ok(new MessageResponse(topicService.deleteTopic(id)));
    }

    @PutMapping("/subscribe/{id}")
    public ResponseEntity<MessageResponse> subscribe(@PathVariable("id") Long id, Authentication authentication) {
        return ResponseEntity.ok(new MessageResponse(topicService.subscribe(id, mddUserService.findUserByUsername(authentication.getName()))));
    }

    @PutMapping("/unsubscribe/{id}")
    public ResponseEntity<MessageResponse> unsubscribe(@PathVariable("id") Long id, Authentication authentication) {
        return ResponseEntity.ok(new MessageResponse(topicService.unsubscribe(id, mddUserService.findUserByUsername(authentication.getName()))));
    }
}