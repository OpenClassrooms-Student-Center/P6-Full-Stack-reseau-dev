package com.openclassrooms.mddapi.mappers;

import com.openclassrooms.mddapi.dtos.TopicDto;
import com.openclassrooms.mddapi.model.Topic;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedSourcePolicy = ReportingPolicy.WARN, unmappedTargetPolicy = ReportingPolicy.WARN,
        typeConversionPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface TopicMapper extends EntityMapper<TopicDto, Topic> {
}
