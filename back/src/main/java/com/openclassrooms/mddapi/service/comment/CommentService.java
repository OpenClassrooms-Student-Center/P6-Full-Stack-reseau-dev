package com.openclassrooms.mddapi.service.comment;

import com.openclassrooms.mddapi.dto.CommentDTO;
import com.openclassrooms.mddapi.dto.DBUserDTO;
import com.openclassrooms.mddapi.model.Comment;
import com.openclassrooms.mddapi.model.DBUser;
import com.openclassrooms.mddapi.model.Post;
import com.openclassrooms.mddapi.repository.CommentRepository;
import com.openclassrooms.mddapi.repository.PostRepository;
import com.openclassrooms.mddapi.util.DateUtils;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService implements ICommentService {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private DateUtils DateUtils;

	private final PostRepository postRepository;

	private final CommentRepository commentRepository;
	
	public CommentService(PostRepository postRepository, CommentRepository commentRepository) {
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
	}

	public List<CommentDTO> getCommentsByPostId(Long postId) {
		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new EntityNotFoundException("Post not found"));
		List<Comment> comments = commentRepository.findByPostId(post);
		return comments.stream()
				.map(entity -> modelMapper.map(entity, CommentDTO.class))
				.collect(Collectors.toList());
	}
	public void createComment(DBUserDTO currentUser, CommentDTO commentDTO) throws RuntimeException {
		Timestamp now = DateUtils.now();

		Comment newComment = modelMapper.map(commentDTO, Comment.class);
		DBUser user = modelMapper.map(currentUser, DBUser.class);
		newComment.setCreatedAt(now);
		newComment.setUserId(user);

		commentRepository.save(newComment);
	}

}