package com.openclassrooms.mddapi;

import com.openclassrooms.mddapi.model.MddUser;
import com.openclassrooms.mddapi.model.Comment;
import com.openclassrooms.mddapi.model.Post;
import com.openclassrooms.mddapi.model.Topic;
import com.openclassrooms.mddapi.properties.RsaKeyProperties;
import com.openclassrooms.mddapi.service.MddUserService;
import com.openclassrooms.mddapi.service.TopicService;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@EnableConfigurationProperties(RsaKeyProperties.class)
@SpringBootApplication
@SecurityScheme(name = "Authorization", description = "Get your token from <b>/api/token</b> and give it valid credentials to get a jwt token. <br> Enter your bearer token in the field " , scheme = "bearer", type = SecuritySchemeType.HTTP, in = SecuritySchemeIn.HEADER)
public class MddApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(MddApiApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(MddUserService usersService, PasswordEncoder encoder, TopicService topicService) {
		return args -> {
            MddUser user = new MddUser();

            // Test values
            user.setId(1L);
            user.setEmail("mail@mail");
            user.setUsername("username");
            user.setPassword(encoder.encode("123"));
            user.setComments(new ArrayList<Comment>());
            user.setPosts(new ArrayList<Post>());
            user.setTopics(new HashSet<>());
            user.setCreatedAt(LocalDateTime.now());
            user.setUpdatedAt(LocalDateTime.now());

			usersService.createUser(user);
			topicService.createTopic(new Topic(null, "angular", "frontend framework",
					new HashSet<>(), LocalDateTime.now(), LocalDateTime.now()));
			topicService.createTopic(new Topic(null, "spring", "backend framework",
					new HashSet<>(), LocalDateTime.now(), LocalDateTime.now()));
			topicService.createTopic(new Topic(null, "mysql", "db system",
					new HashSet<>(), LocalDateTime.now(), LocalDateTime.now()));

		};

	}
}