package com.openclassrooms.mddapi.mappers;

import com.openclassrooms.mddapi.dtos.CommentDto;
import com.openclassrooms.mddapi.model.Comment;
import com.openclassrooms.mddapi.model.MddUser;
import com.openclassrooms.mddapi.model.Post;
import com.openclassrooms.mddapi.service.CommentService;
import com.openclassrooms.mddapi.service.MddUserService;
import com.openclassrooms.mddapi.service.PostService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Component for mapping between Comment and CommentDto objects.
 */
@Component
@Mapper(componentModel = "spring", uses = {CommentService.class}, imports = {Arrays.class, Collectors.class, Comment.class, Post.class, MddUser.class, Collections.class, Optional.class})
public abstract class CommentMapper implements EntityMapper<CommentDto, Comment> {

    @Autowired
    protected MddUserService mddUserService;

    @Autowired
    protected PostService postService;

    /**
     * Maps a CommentDto object to a Comment object.
     *
     * @param commentDto the CommentDto object to map
     * @return the mapped Comment object
     */
    @Mappings({
            @Mapping(target = "author", expression = "java(this.mddUserService.findUserById(commentDto.getAuthorId()))"),
            @Mapping(target = "post", expression = "java(this.postService.findPostById(commentDto.getPostId()))"),
    })
    public abstract Comment toEntity(CommentDto commentDto);

    /**
     * Maps a Comment object to a CommentDto object.
     *
     * @param comment the Comment object to map
     * @return the mapped CommentDto object
     */
    @Mappings({
            @Mapping(target = "postId", source = "post.id"),
            @Mapping(target = "authorId", source = "author.id"),
    })
    public abstract CommentDto toDto(Comment comment);

}