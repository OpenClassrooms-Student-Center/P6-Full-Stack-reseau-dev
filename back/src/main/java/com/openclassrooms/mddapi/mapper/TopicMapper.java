package com.openclassrooms.mddapi.mapper;

import com.openclassrooms.mddapi.dto.PostDto;
import com.openclassrooms.mddapi.dto.TopicDto;
import com.openclassrooms.mddapi.models.Post;
import com.openclassrooms.mddapi.models.Topic;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")

public interface TopicMapper {

    TopicMapper INSTANCE = Mappers.getMapper(TopicMapper.class);
    //@Mapping(target = "id", source = "id") // Mappage explicite de l'ID

    TopicDto toDto(Topic topic);

    Topic toEntity(TopicDto topicDto);
}
