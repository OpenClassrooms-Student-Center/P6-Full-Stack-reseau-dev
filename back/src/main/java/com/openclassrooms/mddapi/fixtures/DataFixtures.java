package com.openclassrooms.mddapi.fixtures;

import com.openclassrooms.mddapi.model.Comment;
import com.openclassrooms.mddapi.model.DBUser;
import com.openclassrooms.mddapi.model.Post;
import com.openclassrooms.mddapi.model.Topic;
import com.openclassrooms.mddapi.repository.CommentRepository;
import com.openclassrooms.mddapi.repository.DBUserRepository;
import com.openclassrooms.mddapi.repository.PostRepository;
import com.openclassrooms.mddapi.repository.TopicRepository;
import com.openclassrooms.mddapi.util.DateUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.HashSet;

@Component
public class DataFixtures implements CommandLineRunner {

    private final DBUserRepository dbUserRepository;
    private final TopicRepository topicRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final DateUtils dateUtils;

    public DataFixtures(
            DBUserRepository dbUserRepository,
            TopicRepository topicRepository,
            PostRepository postRepository,
            CommentRepository commentRepository, DateUtils dateUtils
    )
    {
        this.dbUserRepository = dbUserRepository;
        this.topicRepository = topicRepository;
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
        this.dateUtils = dateUtils;
    }

    @Override
    public void run(String... args) throws Exception {

        commentRepository.deleteAll();
        postRepository.deleteAll();
        dbUserRepository.deleteAll();
        topicRepository.deleteAll();

        Timestamp now = dateUtils.now();

        Topic topic1 = new Topic();
        topic1.setTitle("PHP");
        topic1.setDescription("The new version of PHP is out!");

        Topic topic2 = new Topic();
        topic2.setTitle("Java");
        topic2.setDescription("The new version of Java is out!");

        DBUser user1 = new DBUser();
        user1.setUsername("JohnDoe");
        user1.setEmail("john.doe@email.com");
        // Mypassword8$
        user1.setPassword("$2a$10$iubE9N0INEpjueHwqfKJq.d/Dr2QpWc3l91Z.v7nH1uBMcDdH4X4.");
        user1.setCreatedAt(now);

        user1.setTopics(new HashSet<>(Arrays.asList(topic1, topic2)));


        DBUser user2 = new DBUser();
        user2.setUsername("LoremIpsum");
        user2.setEmail("lorem.ipsum@email.com");
        // Mypassword8$
        user2.setPassword("$2a$10$WWUaHpk6yi9PKDmAv/BekejHy14u.ahqw8HjHmlm7NgKy9xOXs9p.");
        user2.setCreatedAt(now);



        Post post1 = new Post();
        post1.setTitle("PHP 8.2 is out!");
        post1.setContent("content1");
        post1.setTopic(topic1);
        post1.setCreatedAt(now);
        post1.setUserOwner(user1);

        Post post2 = new Post();
        post2.setTitle("Java 17 is out!");
        post2.setContent("content2");
        post2.setTopic(topic2);
        post2.setCreatedAt(now);
        post2.setUserOwner(user2);

        Comment comment1 = new Comment();
        comment1.setContent("Very interesting!");
        comment1.setPost(post1);
        comment1.setUserOwner(user2);
        comment1.setCreatedAt(now);

        Comment comment2 = new Comment();
        comment2.setContent("Awesome!");
        comment2.setPost(post2);
        comment2.setUserOwner(user1);
        comment2.setCreatedAt(now);

        topicRepository.save(topic1);
        topicRepository.save(topic2);

        dbUserRepository.save(user1);
        dbUserRepository.save(user2);

        postRepository.save(post1);
        postRepository.save(post2);

        commentRepository.save(comment1);
        commentRepository.save(comment2);

    }

}