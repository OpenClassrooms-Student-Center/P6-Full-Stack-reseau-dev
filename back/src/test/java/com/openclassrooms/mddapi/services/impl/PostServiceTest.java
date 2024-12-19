package com.openclassrooms.mddapi.services.impl;

import com.openclassrooms.mddapi.dto.PostDto;
import com.openclassrooms.mddapi.mapper.PostMapper;
import com.openclassrooms.mddapi.models.Post;
import com.openclassrooms.mddapi.models.Topic;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.repositories.PostRepository;
import com.openclassrooms.mddapi.services.interfaces.IUserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class PostServiceTest  {

    @Mock
    PostRepository postRepository;
    @Mock
    PostMapper postMapper;
    @Mock
    IUserService userService;
    @Mock
    TopicService topicService;


    @InjectMocks
    PostService postService;
    protected  static User userSubscriber;
    protected static Post post;
    protected  static PostDto postDto;
    protected  static Topic topic;

    @BeforeEach
    void setUp() {
        {
            userSubscriber = User.builder().id(1L).email("test@gmail.com").username("Joseph").password("pwd").build();
            post = Post.builder().id(1L).title("Un livre sur typescript")
                    .content("un langage orienté objet")
                    .topic(Topic.builder().subject("Java").build()).author(userSubscriber).build();
            postDto = PostDto.builder().title("Un livre sur typescript")
                    .content("un langage orienté objet").topicSubject("Java").build();
            topic = Topic.builder().subject("Java").build();
        }
      /*  user = User.builder().firstName("John").lastName("Doe").email("test@gmail.com").password("pwd").build();
        Topic topic = Topic.builder().subject("Test Topic").build();
        post = Post.builder().id(1L).topic(topic).title("Tutorial JUnit").
                content("Avec Maven ou  gradle, JUnit peut être facilement ajouté à votre projet")
                .author(user).build();*/
    }



    @Test
    public void testGetPostById() {
        when(postRepository.findById(1L)).thenReturn(Optional.of(post));
        when(postMapper.mapToPostDto(post)).thenReturn(postDto);
        PostDto foundPost = postService.getPostById(1L);
        Assertions.assertEquals(postDto, foundPost);
        verify(postRepository,times(1)).findById(1L);

    }



    @Test
    public void testGetAllPosts() {
        postRepository.saveAndFlush(post);
        List<Post> expectedPosts = List.of(post);
        when(postRepository.findAll()).thenReturn(List.of(post));
       List<Post> actualPosts = postService.getAllPosts();
        Assertions.assertEquals(expectedPosts, actualPosts);
        verify(postRepository,times(1)).findAll();

    }

    @Test
    @WithMockUser
    public void testCreate_OK() {
        when(postMapper.mapToPost(postDto)).thenReturn(post);
        when(userService.getConnectedUser()).thenReturn(userSubscriber);
        when(postRepository.save(post)).thenReturn(post);
        when(postMapper.mapToPostDto(post)).thenReturn(postDto);
        when(topicService.findBySubject(postDto.getTopicSubject()))
                .thenReturn(Topic.builder().subject("Java").build());

        // Appelle la méthode à tester
        PostDto actualPost = postService.create(postDto);

        // Vérifications
        Assertions.assertNotNull(actualPost);
        Assertions.assertEquals(postDto, actualPost);
        verify(postRepository, times(1)).save(post);
        verify(postMapper, times(1)).mapToPost(postDto);
        verify(postMapper, times(1)).mapToPostDto(post);
    }



}
