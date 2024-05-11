package com.openclassrooms.mddapi.mappers;

import com.openclassrooms.mddapi.dtos.MddUserDto;
import com.openclassrooms.mddapi.dtos.PostDto;
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

@Component
@Mapper(componentModel = "spring", uses = {PostService.class}, imports = {Arrays.class, Collectors.class, Comment.class, Post.class, MddUser.class, Collections.class, Optional.class})
public abstract class PostMapper implements EntityMapper<PostDto, Post> {

    @Autowired
    protected MddUserService mddUserService;

    @Autowired
    protected CommentService commentService;


    @Mappings({
            @Mapping(target = "author", expression = "java(this.mddUserService.findUserById(postDto.getAuthorId()))"),
            @Mapping(target = "comments", expression = "java(this.commentService.findAllByIds(postDto.getCommentIds()))"),
    })
    public abstract Post toEntity(PostDto postDto);
    @Mappings({
            @Mapping(target = "authorId", source = "author.id"),
            @Mapping(target = "commentIds", expression = "java(Optional.ofNullable(post.getComments()).orElseGet(Collections::emptyList).stream().map(u -> u.getId()).collect(Collectors.toList()))"),
    })
    public abstract PostDto toDto(Post post);

}
