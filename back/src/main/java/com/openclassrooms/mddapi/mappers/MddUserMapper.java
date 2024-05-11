package com.openclassrooms.mddapi.mappers;

import com.openclassrooms.mddapi.dtos.MddUserDto;
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
@Mapper(componentModel = "spring", uses = {MddUserService.class}, imports = {Arrays.class, Collectors.class, Comment.class, Post.class, MddUser.class, Collections.class, Optional.class})
public abstract class MddUserMapper implements EntityMapper<MddUserDto, MddUser>{

    @Autowired
    protected CommentService commentService;

    @Autowired
    protected PostService postService;

    @Mappings({
            @Mapping(target = "comments", expression = "java(this.commentService.findAllByIds(mddUserDto.getCommentIds()))"),
            @Mapping(target = "posts", expression = "java(this.postService.findAllByIds(mddUserDto.getPostIds()))"),
    })
    public abstract MddUser toEntity(MddUserDto mddUserDto);
    @Mappings({
            @Mapping(target = "postIds", expression = "java(Optional.ofNullable(mddUser.getPosts()).orElseGet(Collections::emptyList).stream().map(u -> u.getId()).collect(Collectors.toList()))"),
            @Mapping(target = "commentIds", expression = "java(Optional.ofNullable(mddUser.getComments()).orElseGet(Collections::emptyList).stream().map(u -> u.getId()).collect(Collectors.toList()))"),
    })
    public abstract MddUserDto toDto(MddUser mddUser);
}