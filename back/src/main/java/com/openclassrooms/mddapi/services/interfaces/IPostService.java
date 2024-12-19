package com.openclassrooms.mddapi.services.interfaces;

import com.openclassrooms.mddapi.dto.CommentDto;
import com.openclassrooms.mddapi.dto.PostDto;
import com.openclassrooms.mddapi.models.Post;

import java.util.List;

public interface IPostService {
    public List<PostDto> findAllFeeds() ;
    public PostDto getPostById(Long id) ;
    public Post findById(Long id) ;


    public PostDto create(PostDto postDto) ;

    public void createBulk(List<PostDto> postDtoList);
}
