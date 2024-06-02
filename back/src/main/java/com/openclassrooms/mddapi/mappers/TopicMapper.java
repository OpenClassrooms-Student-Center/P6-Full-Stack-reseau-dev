package com.openclassrooms.mddapi.mappers;

import com.openclassrooms.mddapi.dtos.MddUserDto;
import com.openclassrooms.mddapi.dtos.PostDto;
import com.openclassrooms.mddapi.dtos.TopicDto;
import com.openclassrooms.mddapi.model.Comment;
import com.openclassrooms.mddapi.model.MddUser;
import com.openclassrooms.mddapi.model.Post;
import com.openclassrooms.mddapi.model.Topic;
import com.openclassrooms.mddapi.service.MddUserService;
import com.openclassrooms.mddapi.service.PostService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = {PostService.class}, imports = {Arrays.class, Collectors.class, Post.class, MddUser.class, Collections.class, Optional.class})
public abstract class   TopicMapper implements EntityMapper<TopicDto, Topic> {

    public abstract Topic toEntity(TopicDto topicDto);

    public abstract TopicDto toDto(Topic topic);
}
