package com.openclassrooms.mddapi.services.impl;

import com.openclassrooms.mddapi.dto.TopicDto;
import com.openclassrooms.mddapi.mapper.TopicMapper;
import com.openclassrooms.mddapi.models.Subscription;
import com.openclassrooms.mddapi.models.Topic;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.repositories.SubscriptionRepository;
import com.openclassrooms.mddapi.repositories.TopicRepository;
import com.openclassrooms.mddapi.services.interfaces.ITopicService;
import com.openclassrooms.mddapi.services.interfaces.IUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
public class SubscriptionServiceTest {

    @Mock
    private TopicRepository topicRepository;

    @Mock
    private TopicMapper topicMapper;

    @Mock
    private IUserService userService;

    @Mock
    private SubscriptionRepository subscriptionRepository;

    @Mock
    private ITopicService topicService;

    @InjectMocks
    private SubscriptionService subscriptionService;

    private User mockUser;
    private Topic topic1, topic2, topic3;

    @BeforeEach
    void setUp() {
        mockUser = User.builder().id(1L).username("testUser").email("test@gmail.com").build();
        topic1 = Topic.builder().id(1L).subject("Java").description("Programming language").build();
        topic2 = Topic.builder().id(2L).subject("Python").description("Programming language").build();
        topic3 = Topic.builder().id(3L).subject("C++").description("Programming language").build();
    }

    @Test
    void testGetAllTopicsWithSubscriptionStatusForCurrentUser() {
        // Ajouter l'utilisateur au SecurityContextHolder
        when(userService.getConnectedUser()).thenReturn(mockUser);

        // Simuler les abonnements de l'utilisateur
        Subscription subscription2 = Subscription.builder().user(mockUser).topic(topic2).build();
        Subscription subscription3 = Subscription.builder().user(mockUser).topic(topic3).build();

        when(subscriptionRepository.findByUserId(mockUser.getId()))
                .thenReturn(Arrays.asList(subscription2, subscription3));

        // Simuler la liste de tous les topics
        List<Topic> allTopics = Arrays.asList(topic1, topic2, topic3);
        when(topicRepository.findAll()).thenReturn(allTopics);

        // Simuler le mapper pour convertir les topics en TopicDto
        for (Topic topic : allTopics) {
            TopicDto topicDto = TopicDto.builder()
                    .id(topic.getId())
                    .subject(topic.getSubject())
                    .description(topic.getDescription())
                    .build();
            when(topicMapper.toDto(topic)).thenReturn(topicDto);
        }

        // Appeler la méthode
        List<TopicDto> result = subscriptionService.getAllTopicsWithSubscriptionStatusForCurrentUser();

        // Vérifications
        assertEquals(3, result.size());
        assertFalse(result.get(0).isUserSubscribed()); // Topic 1
        assertTrue(result.get(1).isUserSubscribed());  // Topic 2
        assertTrue(result.get(2).isUserSubscribed());  // Topic 3

        // Vérifier les appels mocks
        verify(userService).getConnectedUser();
        verify(subscriptionRepository).findByUserId(mockUser.getId());
        verify(topicRepository).findAll();
        verify(topicMapper, times(3)).toDto(any(Topic.class));
    }
}
