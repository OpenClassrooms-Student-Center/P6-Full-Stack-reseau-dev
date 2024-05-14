package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.dtos.TopicDto;
import com.openclassrooms.mddapi.mappers.TopicMapper;
import com.openclassrooms.mddapi.service.TopicService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/topic")
public class TopicController {

    @Autowired
    TopicService topicService;

    @Autowired
    private TopicMapper topicMapper;

    @GetMapping("/topics")
    public ResponseEntity<List<TopicDto>> getTopics() {
        return ResponseEntity.ok(topicMapper.toDto(topicService.findAllTopics()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TopicDto> getTopicById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(topicMapper.toDto(topicService.findTopicById(id)));
    }

    @PutMapping("/create")
    public ResponseEntity<TopicDto> create(@RequestBody TopicDto topicDto) {
        return ResponseEntity.ok(topicMapper.toDto(topicService.createTopic(topicMapper.toEntity(topicDto))));
    }

    @PostMapping("/update")
    public ResponseEntity<TopicDto> update(@RequestBody TopicDto topicDto) {
        return ResponseEntity.ok(topicMapper.toDto(topicService.updateTopic(topicMapper.toEntity(topicDto))));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id) {
        return ResponseEntity.ok(topicService.deleteTopic(id));
    }
}