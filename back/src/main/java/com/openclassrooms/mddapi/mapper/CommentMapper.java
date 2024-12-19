package com.openclassrooms.mddapi.mapper;

import com.openclassrooms.mddapi.dto.CommentDto;
import com.openclassrooms.mddapi.dto.TopicDto;
import com.openclassrooms.mddapi.models.Comment;
import com.openclassrooms.mddapi.models.Topic;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.openclassrooms.mddapi.dto.PostDto;
import com.openclassrooms.mddapi.dto.TopicDto;
import com.openclassrooms.mddapi.models.Post;
import com.openclassrooms.mddapi.models.Topic;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CommentMapper {

        CommentMapper INSTANCE = Mappers.getMapper(CommentMapper.class);
        @Mapping(target ="postId" ,source="post.id")
        @Mapping(target="username",source ="author.username" )

        CommentDto toDto(Comment comment);

        Comment toEntity(CommentDto commentDto);

        List<CommentDto> toDtoList(List<Comment> comments);
    }
