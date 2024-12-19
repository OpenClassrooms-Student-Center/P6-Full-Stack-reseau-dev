package com.openclassrooms.mddapi.mapper;


import com.openclassrooms.mddapi.dto.PostDto;
import com.openclassrooms.mddapi.models.Post;
import com.openclassrooms.mddapi.models.Topic;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;


@Component
@RequiredArgsConstructor
public class PostMapper {

    public PostDto mapToPostDto(Post post){
        PostDto postDto = PostDto.builder()
                .id(post.getId())
                .content(post.getContent())
                .title(post.getTitle())
                .topicSubject(post.getTopic().getSubject())
                .createdAt(post.getCreatedAt())
                .authorUsername(post.getAuthor().getUsername())
                .build();
        return postDto;
    }

    public List<PostDto> mapToPostDtoList(List<Post> posts){
        return posts.stream().map(this::mapToPostDto).collect(Collectors.toList());
    }


    public Post mapToPost(PostDto postDto){
        Topic topic = Topic.builder().subject(postDto.getTopicSubject()).build();

        Post post = Post.builder()
                .title(postDto.getTitle())
                .content(postDto.getContent())
                .topic(topic)
                .createdAt(postDto.getCreatedAt())
                .build();
        return post;
    }
}