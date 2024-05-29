package com.openclassrooms.mddapi.mappers;

import org.mapstruct.Mapper;
import com.openclassrooms.mddapi.model.Post;
import com.openclassrooms.mddapi.dtos.responses.PostToDisplayResponse;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(unmappedSourcePolicy = ReportingPolicy.WARN, unmappedTargetPolicy = ReportingPolicy.WARN,
        typeConversionPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface ResponsesMapper {

    List<PostToDisplayResponse> postsToPostDisplayResponses(List<Post> posts);
}