package com.openclassrooms.mddapi.controller;

import java.security.Principal;
import java.util.List;
import java.util.Set;

import com.openclassrooms.mddapi.dto.*;
import com.openclassrooms.mddapi.model.Topic;
import com.openclassrooms.mddapi.service.user.IDBUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.openclassrooms.mddapi.service.topic.ITopicService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/topics")
public class TopicController {
	
	private final ITopicService topicService;

	@Autowired
	private IDBUserService dbUserService;

	public TopicController(ITopicService topicService) {
		this.topicService = topicService;
	}

	@Operation(summary = "Get topics", description = "Retrieve all topics")
	@ApiResponses(
		value = {
			@ApiResponse(
				responseCode = "200",
				description = "Topics successfully retrieved",
				content = @Content(
					mediaType = "application/json",
					schema = @Schema(type = "array", implementation = TopicDTO.class),
					examples = @ExampleObject(
						value =
							"[" +
								"{" +
								"\"id\": 1," +
								"\"name\": \"PHP\"," +
								"\"description\": description," +
								"}," +
								"{" +
								"\"id\": 1," +
								"\"name\": \"PHP\"," +
								"\"description\": description," +
								"}" +
							"]"

					)
				)
			),
			@ApiResponse(
					responseCode = "401",
					description = "Unauthorized",
					content = @Content(
						mediaType = "application/json",
						schema = @Schema(type = ""),
						examples = @ExampleObject(
							name = "Unauthorized",
							value = ""
						)
					)
			),
			@ApiResponse(
				responseCode = "400",
				description = "Bad request",
				content = @Content(
					mediaType = "application/json",
					schema = @Schema(implementation = ResponseDTO.class),
					examples = @ExampleObject(
						name = "Bad request",
						value =
								"{" +
									"\"message\": \"{{Error message}}\"" +
								"}"
					)
				)
			)
		}
	)
	@ResponseStatus(HttpStatus.OK)
	@SecurityRequirement(name = "bearer")
	@GetMapping(value = "", produces = "application/json")
	public List<TopicDTO> getTopics() {
		return topicService.getTopics();
	}

	@Operation(summary = "Get topics followed by user", description = "Retrieve all topics for a user")
	@ApiResponses(
		value = {
			@ApiResponse(
				responseCode = "200",
				description = "Topics successfully retrieved",
				content = @Content(
					mediaType = "application/json",
					schema = @Schema(type = "array", implementation = TopicDTO.class),
					examples = @ExampleObject(
						value =
							"[" +
								"{" +
									"\"id\": 1," +
									"\"name\": \"PHP\"," +
									"\"description\": description," +
								"}," +
								"{" +
									"\"id\": 1," +
									"\"name\": \"PHP\"," +
									"\"description\": description," +
								"}" +
							"]"
					)
				)
			),
			@ApiResponse(
				responseCode = "401",
				description = "Unauthorized",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(type = ""),
						examples = @ExampleObject(
								name = "Unauthorized",
								value = ""
						)
				)
			),
			@ApiResponse(
				responseCode = "400",
				description = "Bad request",
				content = @Content(
					mediaType = "application/json",
					schema = @Schema(type = ""),
					examples = @ExampleObject(
						name = "Bad request",
						value =
							"{" +
								"\"message\": \"{{Error message}}\"" +
							"}"
					)
				)
			)
		}
	)
	@ResponseStatus(HttpStatus.OK)
	@SecurityRequirement(name = "bearer")
	@GetMapping(value = "/user", produces = "application/json")
	public List<TopicDTO> getTopicsByUser(Principal user) throws Exception {
		return topicService.getTopicsByUser(user);
	}

	@Operation(summary = "Follow a topic", description = "Follow a topic")
	@ApiResponses(
		value = {
			@ApiResponse(
				responseCode = "200",
				description = "Topic followed !",
				content = @Content(
					mediaType = "application/json",
					schema = @Schema(implementation = ResponseDTO.class),
					examples = @ExampleObject(
						value =
							"{" +
								"\"message\": \"{{Topic followed !}}\"" +
							"}"

					)
				)
			),
			@ApiResponse(
				responseCode = "401",
				description = "Unauthorized",
				content = @Content(
					mediaType = "application/json",
					schema = @Schema(type = ""),
					examples = @ExampleObject(
							name = "Unauthorized",
							value = ""
					)
				)
			),
			@ApiResponse(
				responseCode = "400",
				description = "Bad request",
				content = @Content(
					mediaType = "application/json",
					examples = @ExampleObject(
							name = "Bad request",
							value =
									"{" +
										"\"message\": \"{{Error message}}\"" +
									"}"
					)
				)
			)
		}
	)
	@ResponseStatus(HttpStatus.OK)
	@SecurityRequirement(name = "bearer")
	@PostMapping("{id}/subscribe")
	public List<TopicDTO> subscribe(
			@PathVariable("id") String id,
			Principal user
	) throws Exception {
		topicService.subscribe(user,Long.parseLong(id));
		return this.getTopicsByUser(user);
	}

	@Operation(summary = "Unfollow a topic", description = "Unfollow a topic")
	@ApiResponses(
		value = {
			@ApiResponse(
				responseCode = "200",
				description = "Topic unfollowed !",
				content = @Content(
					mediaType = "application/json",
					schema = @Schema(implementation = ResponseDTO.class),
					examples = @ExampleObject(
						value =
							"{" +
								"\"message\": \"{{Topic followed !}}\"" +
							"}"
					)
				)
			),
			@ApiResponse(
				responseCode = "401",
				description = "Unauthorized",
				content = @Content(
					mediaType = "application/json",
					schema = @Schema(type = ""),
					examples = @ExampleObject(
							name = "Unauthorized",
							value = ""
					)
				)
			),
			@ApiResponse(
				responseCode = "400",
				description = "Bad request",
				content = @Content(
					mediaType = "application/json",
					schema = @Schema(type = ""),
					examples = @ExampleObject(
						name = "Bad request",
						value =
							"{" +
								"\"message\": \"{{Error message}}\"" +
							"}"
					)
				)
			)
		}
	)
	@ResponseStatus(HttpStatus.OK)
	@DeleteMapping("{id}/subscribe")
	public List<TopicDTO> unsubscribe(
			@PathVariable("id") String id,
			Principal user
	) throws Exception {
		topicService.unsubscribe(user,Long.parseLong(id));
		return this.getTopicsByUser(user);
	}

}
