package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.dtos.TopicDto;
import com.openclassrooms.mddapi.dtos.responses.MessageResponse;
import com.openclassrooms.mddapi.mappers.TopicMapper;
import com.openclassrooms.mddapi.model.MddUser;
import com.openclassrooms.mddapi.service.MddUserService;
import com.openclassrooms.mddapi.service.TopicService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

/**
 * TopicController class is responsible for
 * handling the HTTP requests related to the Topic.
 */
@RestController
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/topic")
@SecurityRequirement(name = "Authorization")
public class TopicController {

    @Autowired
    TopicService topicService;

    @Autowired
    private TopicMapper topicMapper;

    @Autowired
    private MddUserService mddUserService;

    /**
     * Finds all available Topics.
     *
     * @return the list of topics wrapped in ResponseEntity.
     */
    @GetMapping("/topics")
    public ResponseEntity<List<TopicDto>> getTopics() {
        return ResponseEntity.ok(topicMapper.toDto(topicService.findAllTopics()));
    }

    /**
     * Gets the Topic by its id.
     *
     * @param id the Topics's id.
     * @return the Topic with the provided id wrapped in ResponseEntity.
     */
    @GetMapping("/{id}")
    public ResponseEntity<TopicDto> getTopicById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(topicMapper.toDto(topicService.findTopicById(id)));
    }

    /**
     * Creates a new Topic.
     *
     * @param topicDto source of data for new Topic.
     * @return the created Topic wrapped in ResponseEntity.
     */
    @PostMapping("/create")
    public ResponseEntity<TopicDto> create(@RequestBody TopicDto topicDto) {
        return ResponseEntity.ok(topicMapper.toDto(topicService.createTopic(topicMapper.toEntity(topicDto))));
    }

    /**
     * Updates a Topic.
     *
     * @param topicDto source of data for Topic update.
     * @return the updated Topic wrapped in ResponseEntity.
     */
    @PutMapping("/update")
    public ResponseEntity<TopicDto> update(@RequestBody TopicDto topicDto) {
        return ResponseEntity.ok(topicMapper.toDto(topicService.updateTopic(topicMapper.toEntity(topicDto))));
    }

    /**
     * Deletes a Topic.
     *
     * @param id id of the Topic being deleted.
     * @return the response message after deletion wrapped in ResponseEntity.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> delete(@PathVariable("id") Long id) {
        return ResponseEntity.ok(new MessageResponse(topicService.deleteTopic(id)));
    }

    /**
     * Subscribes a user to a Topic.
     *
     * @param id the id of the Topic.
     * @param authentication holds the Principal(user) details.
     * @return the response message after subscription wrapped in ResponseEntity.
     */
    @PutMapping("/subscribe/{id}")
    public ResponseEntity<MessageResponse> subscribe(@PathVariable("id") Long id, Authentication authentication) {
        return ResponseEntity.ok(new MessageResponse(topicService.subscribe(id, mddUserService.findUserByEmail(authentication.getName()))));
    }

    /**
     * Unsubscribes a user from a Topic.
     *
     * @param id the id of the Topic.
     * @param authentication holds the Principal(user) details.
     * @return the response message after unsubscription wrapped in ResponseEntity.
     */
    @PutMapping("/unsubscribe/{id}")
    public ResponseEntity<MessageResponse> unsubscribe(@PathVariable("id") Long id, Authentication authentication) {
        return ResponseEntity.ok(new MessageResponse(topicService.unsubscribe(id, mddUserService.findUserByEmail(authentication.getName()))));
    }
}